package com.devhiglevel.productservice.models.documents

import com.devhiglevel.productservice.models.dto.request.ProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("meli-products")
data class MeliProduct(
        @Id val id: String? = null,
        val idPublication: String?,
        val siteId: String?,
        val subTitle: String?,
        val sellerId: String?,
        val title: String?,
        val category: String?,
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
        productDto.categoryId,
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
    )
}

data class Description(val plainText: String)
data class SalesTerms(val id: String, val valueName: String)
data class Pictures(val source: String )
data class Attributes(val id: String, val valueName: String )
