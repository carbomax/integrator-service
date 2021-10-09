package com.devhiglevel.productservice.service

import com.devhiglevel.productservice.models.documents.Category
import com.devhiglevel.productservice.models.dto.request.CategoryDto
import com.devhiglevel.productservice.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.RuntimeException

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    suspend fun save(categoryDto: CategoryDto): Category {
        val categoryFounded = categoryRepository.findByName(categoryDto.name)
        if (categoryFounded != null) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Category with name: ${categoryDto.name} already exist"
        )
        return categoryRepository.save(Category(categoryDto))

    }

    suspend fun update(categoryDto: CategoryDto, id: String): Category {
        val categoryFounded = categoryRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Category with id: $id not found"
        )
        categoryFounded.name = categoryDto.name
        return categoryRepository.save(categoryFounded)
    }

    fun findAll(): Flow<Category> {
        return categoryRepository.findAll()
    }

    suspend fun delete(id: String){
       val categoryToDelete = categoryRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Category with id: $id not found"
        )
        categoryRepository.delete(categoryToDelete)
    }

    suspend fun deleteBatch(ids: List<String>?) {
        if(ids.isNullOrEmpty()) throw ResponseStatusException( HttpStatus.NOT_FOUND, "Empty list to delete categories")
        if(ids.any { it.isNullOrBlank() }) throw ResponseStatusException( HttpStatus.BAD_REQUEST, "List to delete categories contains null or empty ids")
        categoryRepository.deleteAllById(ids)
    }

}