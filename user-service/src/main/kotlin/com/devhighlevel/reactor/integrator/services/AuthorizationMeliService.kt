package com.devhighlevel.reactor.integrator.services

import com.devhighlevel.reactor.integrator.models.dto.response.MeliAuthorizationResponseDto
import com.devhighlevel.reactor.integrator.models.request.AuthMeliUserDto
import com.devhighlevel.reactor.integrator.models.request.MeliUserDto
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class AuthorizationMeliService(
    private val authorizationMeliClient: WebClient,
    private val meliUserService: MeliUserService
    ) {


    @Value("\${authorization.client_secret}")
    private val clientSecret = "";

    @Value("\${authorization.client_id}")
    private val clientId = "";

    @Value("\${authorization.redirect_uri}")
    private val redirectUri = "";

    suspend fun authorization(authMeliUserDto: AuthMeliUserDto): Mono<Map<String, String>> {
       val meliUserDb = meliUserService.findByIdAndUserSystem(authMeliUserDto.idMeliUser!!, authMeliUserDto.idUserSystem!!)
           ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Meli Account with id:${authMeliUserDto.idMeliUser} and idUserSystem:${authMeliUserDto.idUserSystem} is not found ")
        val meliUserDto =  MeliUserDto(meliUserDb)
        return authorizationMeli(authMeliUserDto.code)
                .flatMap { response ->
                    meliUserDto.accessToken = response?.accessToken
                    meliUserDto.tokenType = response?.tokenType
                    meliUserDto.scope = response?.scope
                    meliUserDto.expiresIn = response?.expireIn
                    meliUserDto.refreshToken = response?.refreshToken
                    meliUserDto.userId = response?.userId.toString()
                    save(meliUserDto, meliUserDb.id!!, response)
                    return@flatMap Mono.justOrEmpty( mapOf("Authorized" to "OK") )
                }.onErrorResume {  throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account unauthorized")}
    }

    fun save(meliUserDto: MeliUserDto, id: String, dto: MeliAuthorizationResponseDto){
         val  result = runBlocking {   meliUserService.findByUserId(dto.userId.toString()) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Request to Meli" ) }
       if ( result.filter { it.id != id }.count() > 0) {
           throw ResponseStatusException(HttpStatus.CONFLICT, "Already exist an account with ${dto.userId}")
       }
        runBlocking {  meliUserService.update(meliUserDto, id) }
    }

    fun authorizationMeli(code: String): Mono<MeliAuthorizationResponseDto> {
        val authorizationBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        authorizationBody.add("code", code)
        authorizationBody.add("grant_type", "authorization_code")
        authorizationBody.add("client_id", clientId)
        authorizationBody.add("client_secret", clientSecret)
        authorizationBody.add("redirect_uri", redirectUri)
       return authorizationMeliClient.post()
            .body(BodyInserters.fromFormData(authorizationBody)).retrieve().bodyToMono()
    }
}