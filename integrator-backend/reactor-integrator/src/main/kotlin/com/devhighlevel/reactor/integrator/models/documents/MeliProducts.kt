package com.devhighlevel.reactor.integrator.models.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Products(
    @Id val id: String,
        val idPublication: String,
        val siteId: String,
        val subTitle: String,
        val sellerId: String,
        val title: String,
        val category: String,
        val price: Double,
        val currencyId: String,
        val availableQuantity: Int,
        val buying_mode: String,
        val condition: String = "new",
        val listingTypeId : String = "gold_special",
        val description: Description,
        val videoId : String?,
        val salesTerms: List<SalesTerms>,
        val pictures: List<Pictures>,
        val attributes: List<Attributes>
    )


data class Description(val plainText: String)
data class SalesTerms(val id: String, val valueName: String)
data class Pictures(val source: String )
data class Attributes(val id: String, val valueName: String )
