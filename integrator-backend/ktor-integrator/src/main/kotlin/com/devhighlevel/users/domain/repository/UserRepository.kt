package com.devhighlevel.users.domain.repository

import com.devhighlevel.users.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun create(user: User): User?
    suspend fun getById(id: String): User?
    suspend fun update(user: User): User?
    suspend fun delete(id: String): User?
    suspend fun getAll(): Flow<User>
    suspend fun getByEmail(email: String): User?
}