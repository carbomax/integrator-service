package com.devhighlevel.reactor.integrator.models.documents

import com.devhighlevel.reactor.integrator.models.dto.response.MeliUserResponseDto
import com.devhighlevel.reactor.integrator.models.request.MeliUserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("meli-users")
class MeliUsers(
    @Id
    var id: String? = null,
    val idUserSystem: String?,
    val name: String?,
    val description: String?,
    val tokenType: String?,
    val accessToken: String?,
    val expiresIn: Long?,
    val scope: String?,
    val userId: String?,
    val refreshToken: String?,
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