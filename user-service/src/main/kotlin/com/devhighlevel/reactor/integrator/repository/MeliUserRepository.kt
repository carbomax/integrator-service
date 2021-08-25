package com.devhighlevel.reactor.integrator.repository

import com.devhighlevel.reactor.integrator.models.documents.MeliUsers
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeliUserRepository: CoroutineCrudRepository<MeliUsers, String> {

    suspend fun findByName(name: String): MeliUsers?

    suspend fun findByIdAndIdUserSystem(id: String, idUserSystem: String): MeliUsers?

    suspend fun findByUserId(userId: String): List<MeliUsers>?
}