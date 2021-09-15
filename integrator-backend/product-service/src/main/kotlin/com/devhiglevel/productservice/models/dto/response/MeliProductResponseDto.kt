package com.devhiglevel.productservice.models.dto.response

import com.devhiglevel.productservice.models.documents.*

data class MeliProductResponseDto(
    val id: String?,
    val sku: String?,
    val idPublication: String?,
    val subTitle: String?,
    val title: String?,
    val category: CategoryResponseDto?,
    val price: Double?,
    val currency: String?,
    val availableQuantity: Int?,
    val description: Description?,
    val videoId: String?,
    val pictures: List<Pictures>?,
) {
    constructor(m: MeliProduct) : this(
        m.id,
        m.sku,
        m.idPublication,
        m.subTitle,
        m.title,
        m.category?.let { CategoryResponseDto(it) },
        m.price,
        m.currency,
        m.availableQuantity,
        m.description,
        m.videoId,
        m.pictures
    )
}