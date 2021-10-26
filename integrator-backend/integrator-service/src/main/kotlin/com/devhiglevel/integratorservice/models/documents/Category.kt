package com.devhiglevel.integratorservice.models.documents

import com.devhiglevel.integratorservice.dto.request.CategoryDto
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
