package com.sachin.nutrifyserver.service.impl

import com.sachin.nutrifyserver.entity.Auth
import com.sachin.nutrifyserver.repository.AuthRepository
import com.sachin.nutrifyserver.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl @Autowired constructor(
    private val authRepository: AuthRepository
) : AuthService {

    override fun getToken(userId: String): Auth? =
        try {
            authRepository.findById(userId).get()
        } catch (_ : Exception) {
            null
        }
    override fun updateToken(auth: Auth): Boolean =
        try {
            authRepository.save(auth)
            true
        } catch (_: Exception) {
            false
        }

    override fun removeToken(userId: String): Boolean =
        try {
            authRepository.deleteById(userId)
            true
        } catch (_ : Exception) {
            false
        }

}