package com.devhighlevel.authorizationserver

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@EnableFeignClients
@EnableAuthorizationServer
@SpringBootApplication
class AuthorizationServerApplication(
    val bCryptPasswordEncoder: BCryptPasswordEncoder
): CommandLineRunner {

    override fun run(vararg args: String?) {
       println("Password ${bCryptPasswordEncoder.encode("12345")}")
    }
}

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}
