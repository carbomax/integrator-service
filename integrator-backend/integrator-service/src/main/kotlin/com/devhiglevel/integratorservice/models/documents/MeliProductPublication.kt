package com.devhiglevel.integratorservice.models.documents

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
    )

