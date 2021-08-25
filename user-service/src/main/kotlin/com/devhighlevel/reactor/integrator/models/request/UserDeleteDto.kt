package com.devhighlevel.reactor.integrator.models.request

import javax.validation.constraints.NotNull

data class UserDeleteDto(
    @field:NotNull
    val ids: List<String>?
)
