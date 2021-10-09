package com.devhiglevel.productservice.service

import com.devhiglevel.productservice.models.documents.MeliProduct
import com.devhiglevel.productservice.models.dto.request.DeleteBatchDto
import com.devhiglevel.productservice.models.dto.request.ProductDto
import com.devhiglevel.productservice.repository.ProductRepository
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService(private val productRepository: ProductRepository) {

    val logger: Logger = LoggerFactory.getLogger(ProductService::class.java)
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

    suspend fun findById(id: String): MeliProduct {
        return productRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Product with id: $id not found"
        )
    }

    fun findAll(): Flow<MeliProduct> {
        return productRepository.findAll()
    }

    suspend fun delete(id: String) {
        val product = productRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Product with id $id not found"
        )
        productRepository.delete(product)
    }

    suspend fun deleteBatch(productDeleteBatchDto: DeleteBatchDto): MutableMap<String, List<String>> {
        val ids = productDeleteBatchDto.ids?.distinct()
        if(ids.isNullOrEmpty() || ids.any { it.isNullOrBlank() }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "List to delete products contains null or empty ids")
        }
        val success = mutableListOf<String>()
        val notFound = mutableListOf<String>()
        val results = mutableMapOf<String, List<String>>()

        val productsFound = productRepository.findAllById(ids).map { it.id }.toList()
        if(productsFound.isNotEmpty() && productsFound.none { it.isNullOrBlank() }){
            productRepository.deleteAllById(productsFound as List<String>)
            success.addAll(productsFound)
            val idsNotFound = ids.filterNot { productsFound.contains(it) }
            if(idsNotFound.isNotEmpty()){
                notFound.addAll(idsNotFound)
            }
        } else {
           notFound.addAll(ids)
        }

        results["success"] = success
        results["not_found"] = notFound
        return results
    }


}