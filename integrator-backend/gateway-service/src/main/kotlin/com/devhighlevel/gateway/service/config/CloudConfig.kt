package com.devhighlevel.gateway.service.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
@EnableWebFlux
class CloudConfig : WebFluxConfigurer{

    @Bean
    fun routerLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route{
                it.path("/integrator/**")
                    .uri("lb://integrator-service")
            }
            .route{
                it.path("/oauth/token")
                    .uri("lb://oauth-service")
            }
            .build()
    }



    @Bean
    fun simpleUrlHandlerMapping(): SimpleUrlHandlerMapping? {
        return SimpleUrlHandlerMapping()
    }

    @Bean
    fun corsFilter(): CorsWebFilter? {
        return CorsWebFilter(corsConfigurationSource())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.addAllowedMethod(HttpMethod.GET)
        config.addAllowedMethod(HttpMethod.POST)
        config.addAllowedMethod(HttpMethod.OPTIONS)
        config.addAllowedMethod(HttpMethod.PUT)
        config.addAllowedMethod(HttpMethod.DELETE)
        config.addAllowedOrigin("http://localhost:4200")
        source.registerCorsConfiguration("/**", config)
        return source
    }

}