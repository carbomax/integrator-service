package com.devhiglevel.integratorservice.dto.response

import com.devhiglevel.integratorservice.models.documents.Category


data class CategoryResponseDto(
    val id: String?,
    val name: String?
){
    constructor(category: Category): this(
        category.id,
        category.name
    )
}