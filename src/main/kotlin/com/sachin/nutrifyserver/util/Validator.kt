package com.sachin.nutrifyserver.util

import com.sachin.nutrifyserver.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Validator @Autowired constructor(
    private val authService: AuthService
) {

    fun validateApiKey(apiKey: String): Boolean = true

    fun validateAuthToken(userId: String, token: String): Boolean =
        authService.getToken(userId)?.let { it.authToken == token } ?: false

}