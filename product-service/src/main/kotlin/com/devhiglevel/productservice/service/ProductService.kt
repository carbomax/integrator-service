package com.devhiglevel.productservice.service

import com.devhiglevel.productservice.models.documents.MeliProduct
import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun create(productDto: ProductDto): Mono<MeliProduct> {
        return productRepository.save(MeliProduct(productDto))
    }
}