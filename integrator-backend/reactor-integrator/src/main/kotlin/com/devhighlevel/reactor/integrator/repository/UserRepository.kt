package com.devhighlevel.reactor.integrator.repository

import com.devhighlevel.reactor.integrator.models.documents.Users
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository : CoroutineCrudRepository<Users, String> {
       // fun findByEmail(email: String): Mono<Users>
        suspend fun findByEmail(email: String): Users?
}