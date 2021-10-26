package com.devhiglevel.integratorservice.repository

import com.devhiglevel.integratorservice.models.documents.MeliProduct
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<MeliProduct, String>{
    fun findBySku(sku: String): MeliProduct?
}