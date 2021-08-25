package com.devhighlevel.reactor.integrator.models.request

import com.devhighlevel.reactor.integrator.models.documents.MeliUsers
import com.devhighlevel.reactor.integrator.models.dto.response.MeliUserResponseDto
import java.util.*

data class MeliUserDto (
    val name: String?,
    val idUserSystem: String?,
    val description: String?,
    var accessToken: String?,
    var tokenType: String?,
    var expiresIn: Long?,
    var scope: String?,
    var userId: String?,
    var refreshToken: String?,
    val lastTokenUpdate: Date? = Date()
) {
    constructor(dto: MeliUserResponseDto): this(
        dto.name,
        dto.idUserSystem,
        dto.description,
        dto.accessToken,
        dto.tokenType,
        dto.expiresIn,
        dto.scope,
        dto.userId,
        dto.refreshToken,
        dto.lastTokenUpdate
    )

    constructor(dto: MeliUsers): this(
        dto.name,
        dto.idUserSystem,
        dto.description,
        dto.accessToken,
        dto.tokenType,
        dto.expiresIn,
        dto.scope,
        dto.userId,
        dto.refreshToken,
        dto.lastTokenUpdate
    )
}