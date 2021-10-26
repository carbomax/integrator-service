package com.devhiglevel.integratorservice.services

import com.devhiglevel.integratorservice.dto.request.CategoryDto
import com.devhiglevel.integratorservice.models.documents.Category
import com.devhiglevel.integratorservice.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    suspend fun save(categoryDto: CategoryDto): Category {
        val categoryFounded = categoryRepository.findByName(categoryDto.name!!)
        if (categoryFounded != null) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Category with name: ${categoryDto.name} already exist"
        )
        return categoryRepository.save(Category(categoryDto))

    }

    suspend fun getById(id: String): Category {
        return categoryRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id: $id not found")
    }

    suspend fun update(categoryDto: CategoryDto, id: String): Category {
        val categoryFound = categoryRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Category with id: $id not found"
        )
        categoryFound.name = categoryDto.name
        return categoryRepository.save(categoryFound)
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