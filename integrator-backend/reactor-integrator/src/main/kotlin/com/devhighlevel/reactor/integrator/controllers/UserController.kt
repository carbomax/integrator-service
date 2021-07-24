package com.devhighlevel.reactor.integrator.controllers

import com.devhighlevel.reactor.integrator.models.documents.Users
import com.devhighlevel.reactor.integrator.models.dto.response.UserResponse
import com.devhighlevel.reactor.integrator.models.request.UserDto
import com.devhighlevel.reactor.integrator.services.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid


@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @Value("\${server.port}")
    private val port = "";

    @GetMapping("/ok")
    suspend fun ok(): ResponseEntity<String> {
        return ResponseEntity(port, HttpStatus.OK)
    }

    @GetMapping
    suspend fun users(): ResponseEntity<Flow<UserResponse>> {
        return ResponseEntity(userService.findAll().map { UserResponse(it) }, HttpStatus.OK)
    }

    @PostMapping
    suspend fun create(@Valid @RequestBody user: UserDto): ResponseEntity<Mono<Users>> {
        return ResponseEntity(userService.create(user), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    suspend fun update(@RequestBody user: Users, @PathVariable id: String): ResponseEntity<UserResponse> {
        return ResponseEntity(UserResponse(userService.update(user, id)), HttpStatus.OK)
    }

    @PutMapping("/{id}/{role}")
    suspend fun updateRole(@PathVariable id: String, @PathVariable role: String): ResponseEntity<UserResponse> {
        return ResponseEntity(UserResponse(userService.updateRole(id, role)), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>> {
        userService.deleteById(id)
        return ResponseEntity<Mono<Void>>(HttpStatus.OK)
    }

    @GetMapping("/{id}")
    suspend fun userById(@PathVariable id: String): ResponseEntity<UserResponse> {
        return ResponseEntity(UserResponse(userService.getById(id)), HttpStatus.OK)
    }
}