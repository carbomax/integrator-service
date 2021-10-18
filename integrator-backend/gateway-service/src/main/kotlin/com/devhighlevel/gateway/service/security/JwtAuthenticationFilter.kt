package com.devhighlevel.gateway.service.security

import io.jsonwebtoken.JwtException
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.ClassCastException
import java.time.ZoneId
import java.util.*


@Component
class JwtAuthenticationFilter(
    val authenticationManagerJwt: AuthenticationManagerJwt
): WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        println("Headers" + exchange.request.headers)
       return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
           .filter { authHeader ->
               println("Header $authHeader")
               authHeader.startsWith("Bearer ")
           }
           .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
           .map { token ->
               println("Token $token")
               token.replace("Bearer ", "") }
           .flatMap { token ->

               return@flatMap authenticationManagerJwt.authenticate(UsernamePasswordAuthenticationToken(null, token))
           }
           .flatMap { authentication -> chain.filter(exchange).contextWrite { ReactiveSecurityContextHolder.withAuthentication(authentication) } }
           .onErrorResume(
               Exception::class.java
           ) { e: Exception ->
               val responseBody = mutableMapOf(
                   "code" to HttpStatus.UNAUTHORIZED.name.lowercase(),
                   "status" to "error.authentication",
                   "message" to "malformed token",
                   "timestamp" to Date().toInstant().atZone(ZoneId.of("UTC").normalized()).toString()
               )
               if((e is JwtException || e is AuthenticationException) && e !is ClassCastException ) {
                  responseBody["message"] = e.message ?: "invalid token"
               }

               val buffer: DataBuffer = exchange.response.bufferFactory().wrap(responseBody.toString().toByteArray())
               Mono.fromRunnable<Any> {
                   val response: ServerHttpResponse = exchange.response
                   response.statusCode = HttpStatus.UNAUTHORIZED
               }.then(exchange.response.writeWith ( Flux.just(buffer) ))
           }
    }
}