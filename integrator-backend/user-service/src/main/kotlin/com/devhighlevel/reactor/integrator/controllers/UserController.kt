package com.devhighlevel.reactor.integrator.controllers

import com.devhighlevel.reactor.integrator.models.documents.Users
import com.devhighlevel.reactor.integrator.models.dto.response.UserResponseDto
import com.devhighlevel.reactor.integrator.models.request.UserDeleteDto
import com.devhighlevel.reactor.integrator.models.request.UserDto
import com.devhighlevel.reactor.integrator.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.print.attribute.standard.Media
import javax.validation.Valid


@RestController
@RequestMapping("/system", consumes = arrayOf(""))
class UserController(private val userService: UserService) {

    @Value("\${server.port}")
    private val port = "";

    @GetMapping("/ok")
    suspend fun ok(): ResponseEntity<String> {
        return ResponseEntity(port, HttpStatus.OK)
    }

    @GetMapping
    suspend fun users(): ResponseEntity<Flow<UserResponseDto>> {
        return ResponseEntity(userService.findAll().map { UserResponseDto(it) }, HttpStatus.OK)
    }

    @PostMapping
    suspend fun create(@Valid @RequestBody user: UserDto): ResponseEntity<Mono<Users>> {
        return ResponseEntity(userService.create(user), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    suspend fun update(@RequestBody user: UserDto, @PathVariable id: String): ResponseEntity<UserResponseDto> {
        return ResponseEntity(UserResponseDto(userService.update(user, id)), HttpStatus.OK)
    }

    @PutMapping("/{id}/{role}")
    suspend fun updateRole(@PathVariable id: String, @PathVariable role: String): ResponseEntity<UserResponseDto> {
        return ResponseEntity(UserResponseDto(userService.updateRole(id, role)), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>> {
        userService.deleteById(id)
        return ResponseEntity<Mono<Void>>(HttpStatus.OK)
    }

    @GetMapping("/{id}")
    suspend fun userById(@PathVariable id: String): ResponseEntity<UserResponseDto> {
        return ResponseEntity(UserResponseDto(userService.getById(id)), HttpStatus.OK)
    }

    @GetMapping("/find-by-email/{email}")
    suspend fun userByEmail(@PathVariable email: String): ResponseEntity<UserResponseDto> {
        return ResponseEntity(UserResponseDto(userService.getByEmail(email)), HttpStatus.OK)
    }


    @PutMapping("/enable/{id}/{enable}")
    suspend fun enable(@PathVariable id: String, @PathVariable enable: Boolean): ResponseEntity<UserResponseDto> {
        return ResponseEntity(UserResponseDto(userService.enable(id, enable)), HttpStatus.OK)
    }

    @PatchMapping("/delete-batch")
    suspend fun deleteBatch(@RequestBody userDeleteDto: UserDeleteDto): ResponseEntity<Mono<MutableMap<String, List<String>>>> {
        return ResponseEntity(userService.deleteBatch(userDeleteDto.ids?.distinct()), HttpStatus.OK) 
    }
}