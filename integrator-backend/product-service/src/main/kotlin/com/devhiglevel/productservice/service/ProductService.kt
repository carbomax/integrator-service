package com.devhiglevel.productservice.service

import com.devhiglevel.productservice.models.documents.MeliProduct
import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService(private val productRepository: ProductRepository) {

    suspend fun create(productDto: ProductDto): MeliProduct {
        if (productDto.sku != null) {
            val productFounded = productRepository.findBySku(productDto.sku!!)
            if (productFounded != null) throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product with sku ${productDto.sku}"
            )
        }
        return productRepository.save(MeliProduct(productDto))
    }

    suspend fun update(productDto: ProductDto, id: String): MeliProduct {
        val productFounded = productRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Product with sku ${productDto.sku}"
        )
        productDto.id = productFounded.id
        productDto.sku = productFounded.sku
        return productRepository.save(MeliProduct(productDto))
    }

    fun findAll(): Flow<MeliProduct> {
        return productRepository.findAll()
    }
}