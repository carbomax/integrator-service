package com.devhighlevel.authorizationserver.security.configuration

import com.devhighlevel.authorizationserver.security.events.AuthenticationHandler
import com.devhighlevel.authorizationserver.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class SpringSecurityConfig(
    private val userService: UserService,
    private val eventPublisher: AuthenticationHandler
) : WebSecurityConfigurerAdapter(){


    @Autowired
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(this.userService).passwordEncoder(passwordEncoder())
            .and().authenticationEventPublisher(eventPublisher)
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager? {
        return super.authenticationManager()
    }
}