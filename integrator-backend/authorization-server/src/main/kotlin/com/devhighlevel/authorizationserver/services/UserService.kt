package com.devhighlevel.authorizationserver.services

import com.devhighlevel.authorizationserver.clients.UserClient
import com.devhighlevel.authorizationserver.models.UserDto
import com.devhighlevel.authorizationserver.models.UserResponse
import feign.FeignException
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userClient: UserClient
) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails {
            val userFounded = findByEmail(username) ?: throw UsernameNotFoundException("Login error, user with email: %s ,not fount")
            val authorities: List<GrantedAuthority> = listOf(userFounded.role).map { SimpleGrantedAuthority(it) }

            return User(
                userFounded.email,
                userFounded.password,
                userFounded.enabled ?: false,
                true,
                true,
                true,
                authorities
            )

    }

    fun findByEmail(email: String): UserResponse? {
        return userClient.userByEmail(email)
    }

    suspend fun updateUser(userDto: UserDto, id: String): UserResponse {
        return userClient.update(userDto, id)
    }


}