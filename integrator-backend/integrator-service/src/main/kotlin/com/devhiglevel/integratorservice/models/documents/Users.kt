package com.devhiglevel.integratorservice.models.documents

import com.devhiglevel.integratorservice.dto.request.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Users(
    @Id val id: String?,
    var email: String,
    var password: String?,
    var name: String?,
    var role: String?,
    var image: String?,
    var enabled: Boolean? = false,
    var attempts: Int?
) {
    constructor(user: UserDto) : this(
        null,
        user.email,
        user.password,
        user.name,
        user.role,
        user.image,
        user.enabled,
        user.attempts
    )
}
