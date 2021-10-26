package com.devhiglevel.integratorservice.models.documents

import com.devhiglevel.integratorservice.dto.request.ProductRequestDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("meli-products")
data class MeliProduct(
    @Id val id: String? = null,
    val sku: String?,
    val title: String?,
    var category: Category?,
    val price: Double?,
    val currency: String?,
    val availableQuantity: Int?,
    val description: String?,
    var pictures: List<Pictures>?
    ){
    constructor(productRequestDto: ProductRequestDto): this(
        productRequestDto.id,
        productRequestDto.sku ?: UUID.randomUUID().toString(),
        productRequestDto.title,
        null,
        productRequestDto.price,
        productRequestDto.currencyId,
        productRequestDto.availableQuantity,
        productRequestDto.description,
        productRequestDto.pictures,
    ){
        category = productRequestDto.categoryDto?.let { productRequestDto.categoryDto.id?.let { it1 -> Category(it, it1) } }
    }
}

data class Description(val plainText: String)
data class SalesTerms(val id: String, val valueName: String)
data class Pictures(val source: String )
data class Attributes(val id: String, val valueName: String )
