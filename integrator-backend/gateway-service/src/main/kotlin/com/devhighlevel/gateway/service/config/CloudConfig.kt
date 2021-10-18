package com.devhighlevel.gateway.service.config

import com.devhighlevel.gateway.service.security.JwtAuthenticationFilterGateway
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
@EnableWebFlux
class CloudConfig : WebFluxConfigurer{

    @Bean
    fun routerLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route {
                it.path("/integrator/users/**")
                    .uri("lb://user-service")
            }
            .route{
                it.path("/integrator/products/**")
                    .uri("lb://product-service")
            }
            .route{ it ->
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
    fun corsWebFilter(): CorsWebFilter? {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOriginPatterns = listOf("*")
        corsConfig.maxAge = 3600
        corsConfig.allowedMethods = listOf("*")
        corsConfig.allowedHeaders =
            listOf(
                "Access-Control-Request-Headers",
                "x-requested-with", "authorization",
                "Content-Type", "Content-Length", "Authorization", "credential", "X-XSRF-TOKEN")
        corsConfig.exposedHeaders = listOf(HttpHeaders.SET_COOKIE)
        corsConfig.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return CorsWebFilter(source)
    }

}