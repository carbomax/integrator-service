package com.devhiglevel.integratorservice.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.PostConstruct
import kotlin.io.path.exists
import kotlin.io.path.name

@Configuration
class AppConfig {

    val logger: Logger = LoggerFactory.getLogger(AppConfig::class.java)

    @Value("\${upload.store.path}")
    private val basePath: String = ""

    @Bean
   // fun mongoClient(): MongoClient = MongoClients.create("mongodb+srv://devhighlevel:123456789.*@integratorcluster.cuomw.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
    fun mongoClient(): MongoClient = MongoClients.create("mongodb://localhost")

    @Bean
    fun reactiveMongoTemplate() = ReactiveMongoTemplate(mongoClient(), "integrator")

    @Bean
    fun mapper() = jacksonObjectMapper()

    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder = BCryptPasswordEncoder()

    @PostConstruct
    fun init(){
        createDirectories()
    }

    private fun createDirectories() {
        listOf(
            Path.of(basePath),
            Path.of(basePath).resolve("products"),
            Path.of(basePath).resolve("users")
        ).forEach {
            logger.info("Creating directory: ${it.name}")
            if (!Paths.get(it.toUri()).exists()) {
                Files.createDirectory(it)
            }
        }
    }
}