package com.devhiglevel.integratorservice.repository

import com.devhiglevel.integratorservice.models.documents.Users
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository : CoroutineCrudRepository<Users, String> {
        suspend fun findByEmail(email: String): Users?
}