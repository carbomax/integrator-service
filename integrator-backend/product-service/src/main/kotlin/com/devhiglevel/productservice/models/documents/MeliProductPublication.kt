package com.devhiglevel.productservice.models.documents

import com.devhiglevel.productservice.models.dto.request.ProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("meli-products")
data class MeliProductPublication(
    @Id val id: String? = null,
    val idPublication: String?,
    val siteId: String?,
    val subTitle: String?,
    val sellerId: String?,
    val title: String?,
    var category: Category?,
    val price: Double?,
    val currencyId: String?,
    val availableQuantity: Int?,
    val buyingMode: String?,
    val condition: String = "new",
    val listingTypeId : String = "gold_special",
    val description: Description?,
    val videoId : String?,
    val salesTerms: List<SalesTerms>?,
    val pictures: List<Pictures>?,
    val attributes: List<Attributes>?
    ){
    constructor(productDto: ProductDto): this(
        null,
        productDto.idPublication,
        productDto.siteId,
        productDto.subTitle,
        productDto.sellerId,
        productDto.title,
       null,
        productDto.price,
        productDto.currencyId,
        productDto.availableQuantity,
        productDto.buyingMode,
        productDto.condition,
        productDto.listingTypeId,
        productDto.description,
        productDto.videoId,
        productDto.salesTerms,
        productDto.pictures,
        productDto.attributes
    ){
        category = productDto.categoryDto?.let { productDto.categoryDto.id?.let { it1 -> Category(it, it1) } }
    }
}

