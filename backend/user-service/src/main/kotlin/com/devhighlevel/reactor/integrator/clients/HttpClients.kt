package com.devhighlevel.reactor.integrator.clients

import org.apache.http.HttpHeaders
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class HttpClients {

    @Bean("authorizationMeliClient")
    fun authorizationMeliClient(): WebClient{
       return WebClient.builder()
            .baseUrl("https://api.mercadolibre.com/oauth/token")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()
    }
}