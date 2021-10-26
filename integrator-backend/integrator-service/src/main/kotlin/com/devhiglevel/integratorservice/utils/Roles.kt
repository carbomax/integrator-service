package com.devhiglevel.integratorservice.utils

enum class Roles(val id: Int, val roleName: String) {
    USER(1, "ROLE_USER"),
    ADMIN(2, "ROLE_ADMIN");


    companion object {
        fun of( role: String ) : Roles? {
            return values().find { it.roleName == role }
        }

        fun rolesName(): List<String> {
            return values().map { it.roleName }
        }
    }
}