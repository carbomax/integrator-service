package com.devhiglevel.productservice.models.documents

import com.devhiglevel.productservice.models.dto.request.CategoryDto
import com.devhiglevel.productservice.models.dto.request.ProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("meli-category")
data class Category(
    @Id val id: String? = null,
    var name: String? = null
) {
    constructor(categoryDto: CategoryDto) : this(
        null,
        categoryDto.name
    )

    constructor(categoryDto: CategoryDto, id: String) : this(
        id,
        categoryDto.name
    )
}
