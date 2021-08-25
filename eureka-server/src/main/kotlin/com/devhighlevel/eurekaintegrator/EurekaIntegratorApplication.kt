package com.devhighlevel.eurekaintegrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class EurekaIntegratorApplication

fun main(args: Array<String>) {
    runApplication<EurekaIntegratorApplication>(*args)
}
