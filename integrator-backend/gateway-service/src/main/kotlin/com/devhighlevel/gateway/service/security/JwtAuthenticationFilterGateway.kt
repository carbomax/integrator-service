package com.devhighlevel.gateway.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import java.util.List
import javax.crypto.SecretKey


@Component
class JwtAuthenticationFilterGateway: GatewayFilter {


    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void?>? {
        val request = exchange.request


            if (!request.headers.containsKey("Authorization")) {
                val response = exchange.response
                response.statusCode = HttpStatus.UNAUTHORIZED
                return response.setComplete()
            }
            val token: String = request.headers.getOrEmpty("Authorization")[0]
            try {
                val key : SecretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode("secret_key_123456789-secret_key_123456789".toByteArray()))
             val claims =   Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body

            } catch (e: MalformedJwtException) {
                // e.printStackTrace();
                val response = exchange.response
                response.statusCode = (HttpStatus.BAD_REQUEST)
                return response.setComplete()
            }
        return chain.filter(exchange)
    }

}