package com.devhiglevel.productservice.models.documents

import com.devhiglevel.productservice.models.dto.request.ProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("meli-products")
data class MeliProduct(
    @Id val id: String? = null,
    val sku: String?,
    val idPublication: String?,
    val subTitle: String?,
    val title: String?,
    var category: Category?,
    val price: Double?,
    val currency: String?,
    val availableQuantity: Int?,
    val description: Description?,
    val videoId : String?,
    val pictures: List<Pictures>?
    ){
    constructor(productDto: ProductDto): this(
        productDto.id,
        productDto.sku ?: UUID.randomUUID().toString(),
        productDto.idPublication,
        productDto.subTitle,
        productDto.title,
        null,
        productDto.price,
        productDto.currencyId,
        productDto.availableQuantity,
        productDto.description,
        productDto.videoId,
        productDto.pictures,
    ){
        category = productDto.categoryDto?.let { productDto.categoryDto.id?.let { it1 -> Category(it, it1) } }
    }
}

data class Description(val plainText: String)
data class SalesTerms(val id: String, val valueName: String)
data class Pictures(val source: String )
data class Attributes(val id: String, val valueName: String )
