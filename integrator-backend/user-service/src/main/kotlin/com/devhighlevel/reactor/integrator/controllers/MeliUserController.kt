package com.devhighlevel.reactor.integrator.controllers

import com.devhighlevel.reactor.integrator.models.dto.response.MeliUserResponseDto
import com.devhighlevel.reactor.integrator.models.request.AuthMeliUserDto
import com.devhighlevel.reactor.integrator.models.request.MeliUserDto
import com.devhighlevel.reactor.integrator.models.request.UserDeleteDto
import com.devhighlevel.reactor.integrator.services.AuthorizationMeliService
import com.devhighlevel.reactor.integrator.services.MeliUserService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/users-meli")
class MeliUserController(
    private val meliUserService: MeliUserService,
    private val authorizationMeliService: AuthorizationMeliService
    ) {

    @GetMapping
    suspend fun users(): ResponseEntity<Flow<MeliUserResponseDto>> {
        return ResponseEntity(meliUserService.findAll(), HttpStatus.OK)
    }

    @PostMapping
    suspend fun create(@Valid @RequestBody user: MeliUserDto): ResponseEntity<Mono<MeliUserResponseDto>> {
        return ResponseEntity(meliUserService.create(user), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    suspend fun update(@RequestBody user: MeliUserDto, @PathVariable id: String): ResponseEntity<Mono<MeliUserResponseDto>> {
        return ResponseEntity(meliUserService.update(user, id), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>> {
        meliUserService.deleteById(id)
        return ResponseEntity<Mono<Void>>(HttpStatus.OK)
    }

    @GetMapping("/{id}")
    suspend fun userById(@PathVariable id: String): ResponseEntity<Mono<MeliUserResponseDto>> {
        return ResponseEntity(meliUserService.getById(id), HttpStatus.OK)
    }


    @PutMapping("/enable/{id}/{enable}")
    suspend fun enable(@PathVariable id: String, @PathVariable enable: Boolean): ResponseEntity<Mono<MeliUserResponseDto>> {
        return ResponseEntity(meliUserService.enable(id, enable), HttpStatus.OK)
    }

    @PatchMapping("/delete-batch")
    suspend fun deleteBatch(@RequestBody userDeleteDto: UserDeleteDto): ResponseEntity<Mono<MutableMap<String, List<String>>>> {
        return ResponseEntity(meliUserService.deleteBatch(userDeleteDto.ids?.distinct()), HttpStatus.OK)
    }

    @PostMapping("/authorization")
    suspend fun authorization(@RequestBody authMeliUserDto: AuthMeliUserDto): ResponseEntity<Mono<Map<String, String>>> {
        return ResponseEntity( authorizationMeliService.authorization(authMeliUserDto), HttpStatus.OK)
    }
}