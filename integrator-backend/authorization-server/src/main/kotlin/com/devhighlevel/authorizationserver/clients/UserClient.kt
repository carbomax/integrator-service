package com.devhighlevel.authorizationserver.clients

import com.devhighlevel.authorizationserver.models.UserDto
import com.devhighlevel.authorizationserver.models.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(name = "user-service")
interface UserClient {

    @GetMapping("/integrator/user/user-system/find-by-email/{email}")
    fun userByEmail(@PathVariable email: String): UserResponse?

    @PutMapping("/integrator/user/user-system/{id}")
    fun update(@RequestBody user: UserDto, @PathVariable id: String): UserResponse
}