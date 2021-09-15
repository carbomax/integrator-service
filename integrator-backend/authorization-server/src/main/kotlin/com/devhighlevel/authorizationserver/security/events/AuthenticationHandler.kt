package com.devhighlevel.authorizationserver.security.events

import com.devhighlevel.authorizationserver.models.UserDto
import com.devhighlevel.authorizationserver.models.UserResponse
import com.devhighlevel.authorizationserver.services.UserService
import feign.FeignException
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationHandler(
    val userService: UserService
) : AuthenticationEventPublisher {

    val logger: Logger = LoggerFactory.getLogger(AuthenticationHandler::class.java)

    override fun publishAuthenticationSuccess(authentication: Authentication?) {

        if (authentication?.details is WebAuthenticationDetails) return
        val userDetails = authentication!!.principal as UserDetails
        logger.info("Login success for user with email: ${userDetails.username}")
        val userToUpdated = userService.findByEmail(userDetails.username)
        if (userToUpdated?.attempts != null && userToUpdated.attempts!! > 0) {
            runBlocking {
                userToUpdated.attempts = 0
                userService.updateUser(UserDto(userToUpdated), userToUpdated.id!!)
            }
        }
    }

    override fun publishAuthenticationFailure(exception: AuthenticationException?, authentication: Authentication?) {
        try {
            logger.info("Authenticator error ${exception?.message}")
            val userToUpdated = authentication?.name?.let { userService.findByEmail(it) }
            if (userToUpdated != null && userToUpdated.enabled == true) {
                val userDto = UserDto(userToUpdated)
                when {
                    userToUpdated.attempts == null -> {
                        userDto.attempts = 1
                    }
                    userToUpdated.attempts!! >= 3 -> {
                        userDto.enabled = false
                    }
                    else -> userDto.attempts = userDto.attempts?.plus(1)
                }
                runBlocking { userService.updateUser(userDto, userToUpdated.id!!) }
            } else if (userToUpdated != null && userToUpdated.enabled == false){
                throw DisabledException("Account with email: ${authentication.name} is disabled")
            }

        } catch (ex: FeignException) {
            throw UsernameNotFoundException("Login error, user with email: ${authentication?.name} ,not found")
        }

    }


}