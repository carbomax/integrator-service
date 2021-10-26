package com.devhiglevel.integratorservice.dto.request

import com.devhiglevel.integratorservice.models.documents.Pictures
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductRequestDto (
    var id: String? = null,
    var sku: String? = null,
    val title: String? = null,
    @JsonProperty("category")
    val categoryDto: CategoryDto? = null,
    val price: Double? = null,
    @JsonProperty("currencyId")
    val currencyId: String? = null,
    val availableQuantity: Int? = null,
    val description: String? = null,
    val pictures: List<Pictures>?
)

