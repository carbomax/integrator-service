package com.devhiglevel.integratorservice.dto.response

import com.devhiglevel.integratorservice.models.documents.MeliUsers
import java.util.*

class MeliUserResponseDto(
    val id: String?,
    val name: String?,
    val idUserSystem: String?,
    val description: String?,
    val accessToken: String?,
    val tokenType: String?,
    val expiresIn: Long?,
    val scope: String?,
    val userId: String?,
    val refreshToken: String?,
    val lastTokenUpdate: Date?,
    val enabled: Boolean?
){
    constructor(meliUser: MeliUsers): this(
        meliUser.id,
        meliUser.name,
        meliUser.idUserSystem,
        meliUser.description,
        meliUser.accessToken,
        meliUser.tokenType,
        meliUser.expiresIn,
        meliUser.scope,
        meliUser.userId,
        meliUser.refreshToken,
        meliUser.lastTokenUpdate,
        meliUser.enabled
    )
}