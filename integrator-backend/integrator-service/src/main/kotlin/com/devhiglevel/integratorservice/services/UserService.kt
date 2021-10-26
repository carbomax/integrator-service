package com.devhiglevel.integratorservice.services


import com.devhiglevel.integratorservice.dto.request.UserDto
import com.devhiglevel.integratorservice.models.documents.Users
import com.devhiglevel.integratorservice.repository.UserRepository
import com.devhiglevel.integratorservice.utils.Roles
import kotlinx.coroutines.flow.*
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class UserService(private val userRepository: UserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    suspend fun findAll(): Flow<Users> = userRepository.findAll()


    suspend fun create(userDto: UserDto): Mono<Users> {
        val userFounded = userRepository.findByEmail(userDto.email)
        if (userFounded != null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Already exist user with email:${userDto.email}")
        }
        val userToSave = Users(userDto)
        userToSave.password = bCryptPasswordEncoder.encode(userToSave.password)
        return Mono.justOrEmpty(userRepository.save(userToSave))
    }


    suspend fun getById(id: String): Users {
       return userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
    }

    suspend fun update(user: UserDto, id: String): Users? {
        val userFounded = userRepository.findById(id)
        if(userFounded != null){
            userFounded.name = user.name
            userFounded.email = user.email
            userFounded.password = user.password
            userFounded.role = user.role
            userFounded.attempts = user.attempts
            userFounded.enabled = user.enabled
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

    suspend fun deleteBatch(ids: List<String>?): Mono<MutableMap<String, List<String>>> {
        if(ids.isNullOrEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Empty list to delete users")
        }
        val usersToDelete =  userRepository.findAllById(ids)
        val success = mutableListOf<String>()
        val errors = mutableListOf<String>()
        val results = mutableMapOf<String, List<String>>()

        if(usersToDelete.toList().isEmpty()){
            results["not_founds"] = ids
        }

        usersToDelete.collect {
           try {
               userRepository.delete(it)
               it.id?.let { it1 -> success.add(it1) }
           } catch (ex: Exception) {
               it.id?.let { it1 -> errors.add(it1)}
           }
        }
        results["success"] = success
        results["errors"] = errors
        results["not_founds"] =  results["not_founds"] ?: ids.filterNot { results["success"]?.contains(it) == true || results["errors"]?.contains(it) == true}
        return results.toMono()
    }

    suspend fun enable(id: String, enable: Boolean): Users? {
        val userFounded = userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        if(userFounded.enabled != enable){
            userFounded.enabled = enable
            userFounded.attempts = 0
           return userRepository.save(userFounded)
        }
        return userFounded
    }

    suspend fun getByEmail(email: String): Users {
        return userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by email:${email}")
    }


}