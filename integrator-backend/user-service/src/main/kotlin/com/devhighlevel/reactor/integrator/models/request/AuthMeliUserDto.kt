package com.devhighlevel.reactor.integrator.models.request

data class AuthMeliUserDto(
    val code: String,
    val idUserSystem: String?,
    val idMeliUser: String?
)
