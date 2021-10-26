package com.devhiglevel.integratorservice.models.documents

import com.devhiglevel.integratorservice.dto.request.MeliUserDto
import com.devhiglevel.integratorservice.dto.response.MeliUserResponseDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("meli-users")
class MeliUsers(
    @Id
    var id: String? = null,
    var idUserSystem: String?,
    var name: String?,
    var description: String?,
    var tokenType: String?,
    var accessToken: String?,
    var expiresIn: Long?,
    var scope: String?,
    var userId: String?,
    var refreshToken: String?,
    val lastTokenUpdate: Date? = Date(),
    var enabled: Boolean? = false
) {
    constructor(dto: MeliUserDto, id: String? = null): this(
        id,
        dto.idUserSystem,
        dto.name,
        dto.description,
        dto.tokenType,
        dto.accessToken,
        dto.expiresIn,
        dto.scope,
        dto.userId,
        dto.refreshToken,
        dto.lastTokenUpdate
    )

    constructor(dto: MeliUserResponseDto): this(
        dto.id,
        dto.idUserSystem,
        dto.name,
        dto.description,
        dto.tokenType,
        dto.accessToken,
        dto.expiresIn,
        dto.scope,
        dto.userId.toString(),
        dto.refreshToken,
        Date(),
        dto.enabled
    )
}