package com.devhiglevel.productservice.controller

import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.models.dto.response.MeliProductResponseDto
import com.devhiglevel.productservice.service.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/products")
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
}