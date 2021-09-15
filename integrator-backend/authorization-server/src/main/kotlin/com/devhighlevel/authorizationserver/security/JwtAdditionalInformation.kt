package com.devhighlevel.authorizationserver.security

import com.devhighlevel.authorizationserver.services.UserService
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component

@Component
class JwtAdditionalInformation(
    val userService: UserService
): TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val userResponse = userService.findByEmail(authentication.name)
        val additionalInformation = mutableMapOf<String, Any?>(
            "name"      to userResponse?.name,
            "enabled"   to userResponse?.enabled,
        )
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInformation
        return accessToken
    }
}