package com.devhighlevel.gateway.service.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.SecretKey


@Component
class AuthenticationManagerJwt: ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication.credentials.toString())
            .map { token ->
               val key : SecretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode("secret_key_123456789-secret_key_123456789".toByteArray()))
                return@map Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            }
            .map { claims ->
                val userName = claims.get("user_name", String::class.java)
                val roles = claims.get("authorities", List::class.java) as List<*>
                val authorities = mutableListOf<GrantedAuthority>()
                roles.forEach{
                    authorities.add(SimpleGrantedAuthority(it as String?))
                }
                return@map UsernamePasswordAuthenticationToken(userName, null, authorities)
            }
    }
}