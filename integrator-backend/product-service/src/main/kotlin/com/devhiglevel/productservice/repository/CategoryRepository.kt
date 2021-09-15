package com.devhiglevel.productservice.repository

import com.devhiglevel.productservice.models.documents.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CategoryRepository : CoroutineCrudRepository<Category, String>{

   suspend  fun findByName(name: String): Category?
}