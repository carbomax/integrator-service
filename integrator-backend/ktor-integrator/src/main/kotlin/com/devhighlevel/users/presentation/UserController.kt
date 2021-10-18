package com.devhighlevel.users.presentation

import com.devhighlevel.users.application.UserCommandHandler
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.users(){

    val userCommandHandler: UserCommandHandler by inject()

    post {
        call.respond(HttpStatusCode.Created, userCommandHandler.createUser(call.receive()))
    }

    get {
        call.respond(HttpStatusCode.OK, userCommandHandler.getAllUsers())
    }

    get("/{id}"){
        val userId = call.parameters["id"]
        call.respond( HttpStatusCode.OK, userCommandHandler.getUserById(userId!!))
    }

    get("/search"){
        val email = call.request.queryParameters["email"]
        call.respond(HttpStatusCode.OK, userCommandHandler.search(email!!))
    }

    put ("/{id}"){
        val userId = call.parameters["id"]
        call.respond( HttpStatusCode.OK, userCommandHandler.updateUser(userId!!, call.receive()))
    }

    delete ("/{id}"){
        val userId = call.parameters["id"]
        call.respond(HttpStatusCode.OK, userCommandHandler.deleteUserById(userId!!))
    }

    post("/enable/{id}/{enable}") {
        val userId = call.parameters["id"]
        val enable: String? = call.parameters["enable"]
        call.respond(HttpStatusCode.OK, userCommandHandler.enableOrDisableUser(userId!!, enable.toBoolean()))
    }
}