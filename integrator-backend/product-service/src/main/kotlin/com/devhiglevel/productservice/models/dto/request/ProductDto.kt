package com.devhiglevel.productservice.models.dto.request

import com.devhiglevel.productservice.models.documents.Attributes
import com.devhiglevel.productservice.models.documents.Description
import com.devhiglevel.productservice.models.documents.Pictures
import com.devhiglevel.productservice.models.documents.SalesTerms
import com.fasterxml.jackson.annotation.JsonProperty

class ProductDto (
    var id: String?,
    var sku: String?,
    val idPublication: String?,
    val siteId: String?,
    val subTitle: String?,
    val sellerId: String?,
    val title: String?,
    @JsonProperty("category")
    val categoryDto: CategoryDto?,
    val price: Double?,
    @JsonProperty("currency")
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
)

