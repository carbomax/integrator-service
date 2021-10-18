package com.devhighlevel

import com.devhighlevel.configuration.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit {
    //Init dependencies injection
    ModuleLoader.init()
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureSecurity()
    corsConfiguration()
    configureRouting()
    configureSerialization()
}