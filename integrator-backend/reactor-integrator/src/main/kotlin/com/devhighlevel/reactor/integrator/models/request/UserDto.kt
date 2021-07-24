package com.devhighlevel.reactor.integrator.models.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserDto(
    val id: String?,
    @field:NotNull
    @field:NotBlank
    val email: String,
    @field:NotNull
    @field:NotBlank
    val password: String,
    @field:NotNull
    @field:NotBlank
    val name: String,
    @field:NotNull
    @field:NotBlank
    val role: String,
    val image: String?
)