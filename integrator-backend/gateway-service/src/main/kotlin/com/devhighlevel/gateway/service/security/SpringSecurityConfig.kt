package com.devhighlevel.gateway.service.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
class SpringSecurityConfig(val jwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    fun configure(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity.authorizeExchange()
            .pathMatchers("/oauth/token").permitAll()
            .pathMatchers("/integrator/user/**").permitAll()
            .pathMatchers("/integrator/product/**").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt( jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .csrf().disable()
            .build()
    }
}