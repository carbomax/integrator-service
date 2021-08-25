package com.devhighlevel.reactor.integrator.models.documents

import com.devhighlevel.reactor.integrator.models.request.UserDto
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
    var enabled: Boolean? = false
) {
    constructor(user: UserDto) : this(
        user.id,
        user.email,
        user.password,
        user.name,
        user.role,
        user.image,
        user.enabled
    )
}
