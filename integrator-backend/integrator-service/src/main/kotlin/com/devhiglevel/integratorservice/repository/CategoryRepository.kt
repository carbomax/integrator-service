package com.devhiglevel.integratorservice.repository

import com.devhiglevel.integratorservice.models.documents.Category
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CoroutineCrudRepository<Category, String>{

   suspend  fun findByName(name: String): Category?
}