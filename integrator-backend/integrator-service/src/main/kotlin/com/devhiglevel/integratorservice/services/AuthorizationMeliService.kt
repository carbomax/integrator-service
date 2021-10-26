package com.devhiglevel.integratorservice.services

import com.devhiglevel.integratorservice.dto.request.AuthMeliUserDto
import com.devhiglevel.integratorservice.dto.request.MeliUserDto
import com.devhiglevel.integratorservice.dto.response.MeliAuthorizationResponseDto
import com.devhiglevel.integratorservice.models.documents.MeliUsers
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
    private val meliUserService: MeliUserService,
    private val userService: UserService
) {


    @Value("\${authorization.client_secret}")
    private val clientSecret = "";

    @Value("\${authorization.client_id}")
    private val clientId = "";

    @Value("\${authorization.redirect_uri}")
    private val redirectUri = "";

    suspend fun authorization(authMeliUserDto: AuthMeliUserDto): Mono<Map<String, String>> {
        val meliUserDb =
            meliUserService.findByIdAndUserSystem(authMeliUserDto.idMeliUser!!, authMeliUserDto.idUserSystem!!)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Meli Account with id:${authMeliUserDto.idMeliUser} and idUserSystem:${authMeliUserDto.idUserSystem} is not found "
                )
        userService.getById(meliUserDb.idUserSystem!!)
        return authorizationMeli(authMeliUserDto.code)
            .flatMap { response ->
                meliUserDb.accessToken = response?.accessToken
                meliUserDb.tokenType = response?.tokenType
                meliUserDb.scope = response?.scope
                meliUserDb.expiresIn = response?.expireIn
                meliUserDb.refreshToken = response?.refreshToken
                meliUserDb.userId = response?.userId.toString()
                save(meliUserDb, response)
                return@flatMap Mono.justOrEmpty(mapOf("Authorized" to "OK"))
            }.onErrorResume(Exception::class.java)
            { e ->
                if(e is ResponseStatusException) {
                    throw ResponseStatusException(e.status, "Account unauthorized")
                } else throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Account unauthorized")
            }

    }

    fun save(meliUsers: MeliUsers, dto: MeliAuthorizationResponseDto) {
        val result = runBlocking {
            meliUserService.findByUserId(dto.userId.toString()) ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Bad Request to Meli"
            )
        }
        if (result.filter { it.id != meliUsers.id }.count() > 0) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Already exist an account with ${dto.userId}")
        }
        runBlocking { meliUserService.save(meliUsers) }
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