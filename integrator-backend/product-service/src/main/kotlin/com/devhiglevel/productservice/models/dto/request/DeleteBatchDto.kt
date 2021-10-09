package com.devhiglevel.productservice.models.dto.request

import javax.validation.constraints.NotNull

data class DeleteBatchDto(
    @field:NotNull
    val ids: List<String>?
)
