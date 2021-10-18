package com.devhighlevel.shared.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object Mapper {

    var mapper = ObjectMapper()

    init {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper.disable(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
        )
        mapper.findAndRegisterModules()
        mapper.registerKotlinModule()
    }

    inline fun <reified T> read(string: String): T = mapper.readValue(string)
}