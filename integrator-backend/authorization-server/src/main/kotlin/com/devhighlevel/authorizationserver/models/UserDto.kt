package com.devhighlevel.authorizationserver.models

data class UserDto(
    var id: String?,
    var email: String?,
    var password: String?,
    var name: String?,
    var role: String?,
    var image: String?,
    var enabled: Boolean? = false,
    var attempts: Int? = 0
){
    constructor(userResponse: UserResponse): this(
        userResponse.id,
        userResponse.email,
        userResponse.password,
        userResponse.name,
        userResponse.role,
        userResponse.image,
        userResponse.enabled,
        userResponse.attempts
    )
}