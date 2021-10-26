package com.devhiglevel.integratorservice.dto.response

import com.devhiglevel.integratorservice.models.documents.Description
import com.devhiglevel.integratorservice.models.documents.MeliProduct
import com.devhiglevel.integratorservice.models.documents.Pictures


data class MeliProductResponseDto(
    val id: String? = null,
    val sku: String? = null,
    val title: String? = null,
    val category: CategoryResponseDto? = null,
    val price: Double? = null,
    val currency: String? = null,
    val availableQuantity: Int? = null,
    val description: String? = null,
    val pictures: List<Pictures>? = null
) {
    constructor(m: MeliProduct) : this(
        m.id,
        m.sku,
        m.title,
        m.category?.let { CategoryResponseDto(it) },
        m.price,
        m.currency,
        m.availableQuantity,
        m.description,
        m.pictures
    )
}