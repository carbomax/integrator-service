package com.devhighlevel.shared.infrastructure.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.util.KMongoUtil

interface CoroutineRepository<E : Any> {
    var collection: CoroutineCollection<E>

    suspend fun save(e: E): UpdateResult? {
        return collection.save(e)
    }

    suspend fun findAll(): Flow<E> {
       return collection.find().toFlow()
    }

    suspend fun findById(id: String): E? {
        return collection.findOne("{_id : '$id'}")
    }

    suspend fun deleteOneById(id: String): E? {
        return collection.findOneAndDelete(Filters.eq("_id", id))
    }

    suspend fun find(filters: String): CoroutineFindPublisher<E> {
        return collection.find(filters)
    }
}