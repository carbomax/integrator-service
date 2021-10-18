package com.devhighlevel.users.application

import com.devhighlevel.users.domain.entities.User
import com.devhighlevel.users.domain.repository.UserRepository
import io.ktor.features.*
import kotlinx.coroutines.flow.toList

class UserCommandHandler(private val userRepository: UserRepository) {

    suspend fun createUser(user: User): User {
        if(userRepository.getByEmail(user.email) != null) {
            throw RuntimeException("User by email: ${user.email} not found.")
        }
        return userRepository.create(user) ?: throw RuntimeException("User: $user not created.")
    }

    suspend fun getUserById(userId: String): User {
        return userRepository.getById(userId) ?: throw NotFoundException("User not found with id:$userId.")
    }

    suspend fun updateUser(userId: String, user: User): User {
        val userFound = userRepository.getById(userId) ?: throw NotFoundException("User not found with id:$userId.")
        userFound.email = user.email
        userFound.image = user.image ?: userFound.image
        userFound.name = user.name ?: userFound.name
        userFound.role = user.role ?: userFound.role
        return userRepository.update(userFound) ?: throw RuntimeException("User: $user not updated.")
    }

    suspend fun deleteUserById(userId: String): User {
        val userFound = userRepository.getById(userId) ?: throw NotFoundException("User not found with id:$userId")
        return userRepository.delete(userFound.id!!) ?: throw RuntimeException("User: $userFound not deleted.")
    }

    suspend fun getAllUsers(): List<User> {
        return userRepository.getAll().toList()
    }

    suspend fun search(email: String): User {
        return userRepository.getByEmail(email) ?: throw RuntimeException("User by email: $email not found.")
    }

    suspend fun enableOrDisableUser(userId: String, enable: Boolean): User {
        val userFound = userRepository.getById(userId) ?: throw NotFoundException("User not found with id:$userId.")
        if(userFound.enabled != enable){
            userFound.enabled = enable
            userFound.attempts = 0
            return userRepository.update(userFound) ?: throw RuntimeException("User: $userFound not updated.")
        }
        return userFound
    }

}