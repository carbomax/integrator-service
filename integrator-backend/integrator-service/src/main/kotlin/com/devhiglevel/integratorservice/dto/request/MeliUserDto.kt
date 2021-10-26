package com.devhiglevel.integratorservice.dto.request

import com.devhiglevel.integratorservice.dto.response.MeliUserResponseDto
import com.devhiglevel.integratorservice.models.documents.MeliUsers
import java.util.*

data class MeliUserDto (
    val name: String?,
    var idUserSystem: String?,
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