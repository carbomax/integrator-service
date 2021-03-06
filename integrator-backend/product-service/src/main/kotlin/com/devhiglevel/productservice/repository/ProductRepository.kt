package com.devhiglevel.productservice.repository

import com.devhiglevel.productservice.models.documents.MeliProduct
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<MeliProduct, String>{
    fun findBySku(sku: String): MeliProduct?
}