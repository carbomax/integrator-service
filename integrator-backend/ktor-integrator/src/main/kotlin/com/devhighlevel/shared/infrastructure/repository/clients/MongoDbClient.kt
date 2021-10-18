package com.devhighlevel.shared.infrastructure.repository.clients

import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


object MongoDbClient {

    val table : CoroutineDatabase = KMongo.createClient().coroutine.getDatabase("ktor-crud")

    inline fun <reified T : Any> collection(): CoroutineCollection<T> {
        return table.getCollection()
    }
}