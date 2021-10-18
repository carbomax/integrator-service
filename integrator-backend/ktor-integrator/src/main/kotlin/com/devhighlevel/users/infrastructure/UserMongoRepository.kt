package com.devhighlevel.users.infrastructure

import com.devhighlevel.shared.infrastructure.repository.CoroutineRepository
import com.devhighlevel.shared.infrastructure.repository.clients.MongoDbClient
import com.devhighlevel.users.domain.entities.User
import com.devhighlevel.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.litote.kmongo.coroutine.CoroutineCollection

class UserMongoRepository: UserRepository, CoroutineRepository<User> {
    override var collection: CoroutineCollection<User> = MongoDbClient.collection()

    override suspend fun create(user: User): User? {
        return createOrSave(user)
    }

    override suspend fun getById(id: String): User? {
        return this.findById(id)
    }

    override suspend fun update(user: User): User? {
        return createOrSave(user)
    }

    override suspend fun delete(id: String): User? {
       return this.deleteOneById(id)
    }

    override suspend fun getAll(): Flow<User> {
        return this.findAll()
    }

    override suspend fun getByEmail(email: String): User? {
        return this.find("{ email: '$email' }").first()
    }

    private suspend fun createOrSave(user: User): User? {
        this.save(user)
        var result: User? = null
        if (!user.id.isNullOrBlank()) {
            result = user
        }
        return result
    }


}