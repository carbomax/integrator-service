package com.devhiglevel.integratorservice.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeliAuthorizationResponseDto(
    @JsonProperty("access_token")
    val accessToken: String?,
    @JsonProperty("token_type")
    val tokenType: String?,
    @JsonProperty("expires_in")
    val expireIn: Long?,
    val scope: String?,
    @JsonProperty("user_id")
    val userId: Long?,
    @JsonProperty("refresh_token")
    val refreshToken: String?
)
