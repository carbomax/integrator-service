package com.devhiglevel.integratorservice.dto.request

data class AuthMeliUserDto(
    val code: String,
    val idUserSystem: String?,
    val idMeliUser: String?
)
