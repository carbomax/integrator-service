package com.devhighlevel.reactor.integrator.services

import com.devhighlevel.reactor.integrator.models.documents.MeliUsers
import com.devhighlevel.reactor.integrator.models.dto.response.MeliUserResponseDto
import com.devhighlevel.reactor.integrator.models.request.MeliUserDto
import com.devhighlevel.reactor.integrator.repository.MeliUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class MeliUserService(private val meliUserRepository: MeliUserRepository) {

    suspend fun findAll(): Flow<MeliUserResponseDto>  {
       return meliUserRepository.findAll().map { MeliUserResponseDto(it) }
    }

    suspend fun findByIdAndUserSystem(id: String, idUserSystem: String): MeliUsers? {
        return meliUserRepository.findByIdAndIdUserSystem(id, idUserSystem)
    }

    suspend fun findByUserId(userId: String): List<MeliUsers>? {
        return meliUserRepository.findByUserId(userId)
    }

    suspend fun create(meliUserDto: MeliUserDto): Mono<MeliUserResponseDto>? {
        val userFounded = meliUserDto.name?.let { meliUserRepository.findByName(it) }
        if (userFounded != null){
            throw ResponseStatusException(HttpStatus.CONFLICT, "Already exist user with name:${meliUserDto.name}")
        }
        val userToSave = MeliUsers(meliUserDto)
        return Mono.justOrEmpty(MeliUserResponseDto(meliUserRepository.save(userToSave)))
    }


    suspend fun getById(id: String): Mono<MeliUserResponseDto>? {
        val userFounded = meliUserRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        return Mono.justOrEmpty(MeliUserResponseDto(userFounded))
    }

    suspend fun update(meliUserDto: MeliUserDto, id: String): Mono<MeliUserResponseDto>? {
        val userFounded = meliUserRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
            if(userFounded.name != meliUserDto.name){
                val userByName = meliUserDto.name?.let { meliUserRepository.findByName(it) }
                if(userByName != null){
                    throw ResponseStatusException(HttpStatus.CONFLICT, "Already exist user with name:${meliUserDto.name}")
                }
            }
            val userToUpdate = MeliUsers(meliUserDto, id)
            return Mono.justOrEmpty(MeliUserResponseDto(meliUserRepository.save(userToUpdate)))
    }

    suspend fun deleteById(id: String) {
        meliUserRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        meliUserRepository.deleteById(id)
    }


    suspend fun deleteBatch(ids: List<String>?): Mono<MutableMap<String, List<String>>> {
        if(ids.isNullOrEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Empty list to delete users")
        }
        val usersToDelete =  meliUserRepository.findAllById(ids)
        val success = mutableListOf<String>()
        val errors = mutableListOf<String>()
        val results = mutableMapOf<String, List<String>>()

        if(usersToDelete.toList().isEmpty()){
            results["not_founds"] = ids
        }

        usersToDelete.collect {
            try {
                meliUserRepository.delete(it)
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

    suspend fun enable(id: String, enable: Boolean): Mono<MeliUserResponseDto>? {
        var userFounded = meliUserRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not founded by id:$id")
        if(userFounded.enabled != enable){
            userFounded.enabled = enable
            userFounded = meliUserRepository.save(userFounded)
        }
        return Mono.justOrEmpty(MeliUserResponseDto(userFounded))
    }

}