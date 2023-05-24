package me.dio.ednascimento.creditapplicationsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val user: UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("123")
            .roles("USER")
            .build()

        val admin: UserDetails = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("123")
            .roles("MANAGES")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }
}