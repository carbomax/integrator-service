package com.devhighlevel.reactor.integrator.models.dto.response

import com.devhighlevel.reactor.integrator.models.documents.Users

class UserResponse(
    val id: String?,
    val email: String?,
    val password: String?,
    val name: String?,
    val role: String?
) {
    constructor(user: Users?): this(
        user?.id,
        user?.email,
        user?.password,
        user?.name,
        user?.role
    )
}