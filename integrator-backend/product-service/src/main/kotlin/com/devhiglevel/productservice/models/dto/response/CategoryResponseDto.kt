package com.devhiglevel.productservice.models.dto.response

import com.devhiglevel.productservice.models.documents.Category

data class CategoryResponseDto(
    val id: String?,
    val name: String?
){
    constructor(category: Category): this(
        category.id,
        category.name
    )
}