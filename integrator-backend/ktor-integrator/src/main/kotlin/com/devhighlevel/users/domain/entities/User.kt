package com.devhighlevel.users.domain.entities

import org.bson.codecs.pojo.annotations.BsonId

data class User(
    @BsonId
    val id: String?,
    var email: String,
    var password: String?,
    var name: String?,
    var role: String?,
    var image: String?,
    var enabled: Boolean? = false,
    var attempts: Int?
)
