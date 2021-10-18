package com.devhighlevel.configuration.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.devhighlevel.users.presentation.users
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import java.util.*

fun Application.configureRouting() {

    routing {
        get("/auth/token") {
            val jwtAudience = environment.config.property("jwt.audience").getString()
            val realm = environment.config.property("jwt.realm").getString()
            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(environment.config.property("jwt.domain").getString())
                .withClaim("username", "luis")
                .withExpiresAt(Date(System.currentTimeMillis() + 600000))
                .sign(Algorithm.HMAC256("secret"))
            call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
        }

        route("/integrator") {
            route("/users") {
                users()
            }

            route("/products") {

            }
        }

        authenticate("auth-jwt") {
            get("products") {
                call.respond(
                    mapOf(
                        "response" to "get authenticated value from token " +
                                "name = ${call.principal<JWTPrincipal>()?.payload}"
                    )
                )
            }
        }
    }
}
