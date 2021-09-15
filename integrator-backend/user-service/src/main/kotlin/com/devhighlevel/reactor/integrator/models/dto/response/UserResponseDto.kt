package com.devhighlevel.reactor.integrator.models.dto.response

import com.devhighlevel.reactor.integrator.models.documents.Users

class UserResponseDto(
    val id: String?,
    val email: String?,
    val password: String?,
    val name: String?,
    val role: String?,
    val image: String?,
    val enabled: Boolean?,
    val attempts: Int?
) {
    constructor(user: Users?): this(
        user?.id,
        user?.email,
        user?.password,
        user?.name,
        user?.role,
        user?.image,
        user?.enabled,
        user?.attempts
    )
}