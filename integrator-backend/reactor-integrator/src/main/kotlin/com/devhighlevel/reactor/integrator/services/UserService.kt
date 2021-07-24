package com.devhighlevel.reactor.integrator.services

import com.devhighlevel.reactor.integrator.models.documents.Users
import com.devhighlevel.reactor.integrator.models.request.UserDto
import com.devhighlevel.reactor.integrator.repository.UserRepository
import com.devhighlevel.reactor.integrator.utils.enums.Roles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class UserService(private val userRepository: UserRepository) {

    suspend fun findAll(): Flow<Users> = userRepository.findAll()


    suspend fun create(userDto: UserDto): Mono<Users> {
        val userFounded = userRepository.findByEmail(userDto.email)
        if (userFounded != null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by email:${userDto.email}")
        }
        return Mono.justOrEmpty(userRepository.save(Users(userDto)))
    }


    suspend fun getById(id: String): Users? {
       return userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
    }

    suspend fun update(user: Users, id: String): Users? {
        val userFounded = userRepository.findById(id)
        if(userFounded != null){
            userFounded.name = user.name
            userFounded.email = user.email
            userFounded.password = user.password
            userFounded.role = user.role
            return userRepository.save(userFounded)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
    }

    suspend fun deleteById(id: String) {
        userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        userRepository.deleteById(id)
    }

    suspend fun updateRole(id: String, role: String): Users? {
        val roleToUpdate = Roles.of(role)?.roleName ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Role [$role] not allowed. Try with: ${Roles.rolesName()}")
        val userToUpdate = userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        userToUpdate.role = roleToUpdate
        return userRepository.save(userToUpdate)
    }
}