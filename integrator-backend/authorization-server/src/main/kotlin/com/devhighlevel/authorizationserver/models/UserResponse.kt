package com.devhighlevel.authorizationserver.models

class UserResponse(
    val id: String?,
    val email: String?,
    val password: String?,
    val name: String?,
    val role: String?,
    val image: String?,
    val enabled: Boolean?,
    var attempts: Int?
)