package com.devhiglevel.productservice.controller

import com.devhiglevel.productservice.models.dto.request.DeleteBatchDto
import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.models.dto.response.MeliProductResponseDto
import com.devhiglevel.productservice.service.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
class ProductController(private val productService: ProductService) {

    @PostMapping
    suspend fun create(@RequestBody productDto: ProductDto): ResponseEntity<Mono<MeliProductResponseDto>>{
        return ResponseEntity(Mono.justOrEmpty(MeliProductResponseDto(productService.create(productDto))), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    suspend fun update(@RequestBody productDto: ProductDto, @PathVariable id: String): ResponseEntity<Mono<MeliProductResponseDto>>{
        return ResponseEntity(Mono.justOrEmpty(MeliProductResponseDto(productService.update(productDto, id))), HttpStatus.OK)
    }

    @GetMapping
    fun products(): ResponseEntity<Flow<MeliProductResponseDto>>{
        return ResponseEntity(productService.findAll().map { MeliProductResponseDto(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: String): ResponseEntity<Mono<MeliProductResponseDto>>{
        return ResponseEntity(Mono.justOrEmpty(MeliProductResponseDto(productService.findById(id))), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Mono<Void>>{
        productService.delete(id)
        return ResponseEntity<Mono<Void>>(HttpStatus.OK)
    }

    @PatchMapping("/delete/batch")
    suspend fun deleteBatch(@RequestBody productDeleteBatchDto: DeleteBatchDto): ResponseEntity<Mono<MutableMap<String, List<String>>>> {
        return ResponseEntity(productService.deleteBatch(productDeleteBatchDto).toMono(), HttpStatus.OK)
    }
}