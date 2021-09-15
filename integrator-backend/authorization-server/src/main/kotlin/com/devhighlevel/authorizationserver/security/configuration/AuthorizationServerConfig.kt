package com.devhighlevel.authorizationserver.security.configuration

import com.devhighlevel.authorizationserver.security.JwtAdditionalInformation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.util.*


@Configuration
class AuthorizationServerConfig(
    val bCryptPasswordEncoder: BCryptPasswordEncoder,
    val authenticationManager: AuthenticationManager,
    val jwtAdditionalInformation: JwtAdditionalInformation
) : AuthorizationServerConfigurerAdapter(){


    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients?.inMemory()?.withClient("integrator-frontend")
            ?.secret(bCryptPasswordEncoder.encode("12345"))
            ?.scopes("read", "write")
            ?.authorizedGrantTypes("password", "refresh_token")
            ?.accessTokenValiditySeconds(21600)
            ?.refreshTokenValiditySeconds(21600);
    }

    @Throws(java.lang.Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(jwtAdditionalInformation, accessTokenConverter()))
        endpoints.authenticationManager(this.authenticationManager)
            .tokenStore(tokenStorage())
            .accessTokenConverter(accessTokenConverter())
            .tokenEnhancer(tokenEnhancerChain)
    }

    @Bean
    fun tokenStorage(): JwtTokenStore? {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter? {
        val tokenConverter = JwtAccessTokenConverter()
        tokenConverter.setSigningKey(Base64.getEncoder().encodeToString("secret_key_123456789-secret_key_123456789".toByteArray()))
        return tokenConverter
    }
}