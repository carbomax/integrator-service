package com.devhighlevel.gateway.service.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class JwtAuthenticationFilter(
    val authenticationManagerJwt: AuthenticationManagerJwt
): WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
       return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
           .filter { authHeader -> authHeader.startsWith("Bearer ") }
           .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
           .map { token -> token.replace("Bearer ", "") }
           .flatMap { token -> return@flatMap authenticationManagerJwt.authenticate(UsernamePasswordAuthenticationToken(null, token)) }
           .flatMap { authetication -> chain.filter(exchange).contextWrite { ReactiveSecurityContextHolder.withAuthentication(authetication) } }
    }
}