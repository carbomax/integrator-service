package com.devhiglevel.productservice.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@Configuration
class AppConfig {

    @Bean
   // fun mongoClient(): MongoClient = MongoClients.create("mongodb+srv://devhighlevel:123456789.*@integratorcluster.cuomw.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
    fun mongoClient(): MongoClient = MongoClients.create("mongodb://localhost")

    @Bean
    fun reactiveMongoTemplate() = ReactiveMongoTemplate(mongoClient(), "integrator")

    @Bean
    fun mapper() = jacksonObjectMapper()
}