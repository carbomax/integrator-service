package com.devhiglevel.integratorservice.services

import com.devhiglevel.integratorservice.dto.request.DeleteBatchDto
import com.devhiglevel.integratorservice.dto.request.ProductRequestDto
import com.devhiglevel.integratorservice.models.documents.Category
import com.devhiglevel.integratorservice.models.documents.MeliProduct
import com.devhiglevel.integratorservice.repository.ProductRepository
import com.devhiglevel.integratorservice.services.upload.UploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Path

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryService: CategoryService
) {

    val logger: Logger = LoggerFactory.getLogger(ProductService::class.java)

    @Value("\${upload.store.path}")
    private val basePath: String = ""

    suspend fun create(productRequestDto: ProductRequestDto): MeliProduct {
        if (productRequestDto.sku != null) {
            val productFounded = productRepository.findBySku(productRequestDto.sku!!)
            if (productFounded != null) throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product with sku ${productRequestDto.sku}"
            )
        }
        if (productRequestDto.categoryDto?.id == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id can not be null")
        }
        val categoryFound = categoryService.getById(productRequestDto.categoryDto.id)
        val productToSave = MeliProduct(productRequestDto)
        productToSave.category = categoryFound
        return productRepository.save(productToSave)
    }

    suspend fun update(productRequestDto: ProductRequestDto, id: String): MeliProduct {
        val productFounded = productRepository.findById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Product with sku ${productRequestDto.sku}"
        )
        productRequestDto.id = productFounded.id
        productRequestDto.sku = productFounded.sku
        return this.update(MeliProduct(productRequestDto))
    }

    suspend fun update(meliProduct: MeliProduct): MeliProduct {
        return productRepository.save(meliProduct)
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
        val images = product.pictures?.map { it.source }
        if (images != null) {
            withContext(Dispatchers.IO) {
                images.forEach {
                    launch {
                        Files.deleteIfExists(Path.of(basePath).resolve("products").resolve(it))
                    }
                }

            }
        }

    }

    suspend fun deleteBatch(productDeleteBatchDto: DeleteBatchDto): MutableMap<String, List<String>> {
        val ids = productDeleteBatchDto.ids?.distinct()
        if (ids.isNullOrEmpty() || ids.any { it.isNullOrBlank() }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "List to delete products contains null or empty ids")
        }
        val success = mutableListOf<String>()
        val notFound = mutableListOf<String>()
        val results = mutableMapOf<String, List<String>>()

        val productsFound = productRepository.findAllById(ids).map { it.id }.toList()
        if (productsFound.isNotEmpty() && productsFound.none { it.isNullOrBlank() }) {
            productRepository.deleteAllById(productsFound as List<String>)
            success.addAll(productsFound)
            val idsNotFound = ids.filterNot { productsFound.contains(it) }
            if (idsNotFound.isNotEmpty()) {
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