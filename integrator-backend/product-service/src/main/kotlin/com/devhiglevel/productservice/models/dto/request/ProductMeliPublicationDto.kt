package com.devhiglevel.productservice.models.dto.request

import com.devhiglevel.productservice.models.documents.Attributes
import com.devhiglevel.productservice.models.documents.Description
import com.devhiglevel.productservice.models.documents.Pictures
import com.devhiglevel.productservice.models.documents.SalesTerms

class ProductMeliPublicationDto (
    val idPublication: String?,
    val siteId: String?,
    val subTitle: String?,
    val sellerId: String?,
    val title: String?,
    val categoryId: String?,
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
)

