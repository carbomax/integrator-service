package com.devhiglevel.productservice.controller

import com.devhiglevel.productservice.models.documents.MeliProduct
import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun create(@RequestBody productDto: ProductDto): ResponseEntity<Mono<MeliProduct>>{
        return ResponseEntity(productService.create(productDto), HttpStatus.OK)
    }
}