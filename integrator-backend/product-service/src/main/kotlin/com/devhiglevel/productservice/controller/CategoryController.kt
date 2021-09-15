package com.devhiglevel.productservice.controller

import com.devhiglevel.productservice.models.dto.request.CategoryDto
import com.devhiglevel.productservice.models.dto.response.CategoryResponseDto
import com.devhiglevel.productservice.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/category")
class CategoryController(val categoryService: CategoryService) {

    @PostMapping
    suspend fun create(@RequestBody categoryDto: CategoryDto): ResponseEntity<Mono<CategoryResponseDto>> {
        return ResponseEntity(Mono.justOrEmpty(CategoryResponseDto(categoryService.save(categoryDto))), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    suspend fun update(@RequestBody categoryDto: CategoryDto, @PathVariable id: String): ResponseEntity<Mono<CategoryResponseDto>> {
        return ResponseEntity(Mono.justOrEmpty(CategoryResponseDto(categoryService.update(categoryDto, id))), HttpStatus.OK)
    }

    @GetMapping
    fun categories(): ResponseEntity<Flow<CategoryResponseDto>> {
        return ResponseEntity(categoryService.findAll().map { CategoryResponseDto(it) }, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>> {
        categoryService.delete(id)
        return ResponseEntity<Mono<Void>>(HttpStatus.OK)
    }

}