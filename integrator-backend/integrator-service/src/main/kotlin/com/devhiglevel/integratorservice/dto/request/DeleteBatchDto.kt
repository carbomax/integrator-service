package com.devhiglevel.integratorservice.dto.request

import javax.validation.constraints.NotNull

data class DeleteBatchDto(
    @field:NotNull
    val ids: List<String>?
)
