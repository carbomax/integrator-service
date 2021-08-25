package com.devhighlevel.reactor.integrator.configuration

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AppConfig {

    @Bean
   // fun mongoClient(): MongoClient = MongoClients.create("mongodb+srv://devhighlevel:123456789.*@integratorcluster.cuomw.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
    fun mongoClient(): MongoClient = MongoClients.create("mongodb://localhost")

    @Bean
    fun reactiveMongoTemplate() = ReactiveMongoTemplate(mongoClient(), "integrator")

    @Bean
    fun mapper() = jacksonObjectMapper()

    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder = BCryptPasswordEncoder()
}