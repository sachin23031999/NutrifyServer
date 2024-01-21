package com.sachin.nutrifyserver.service

import com.sachin.nutrifyserver.entity.Auth

interface AuthService {

    fun getToken(userId: String): Auth?
    fun updateToken(auth: Auth): Boolean
    fun removeToken(userId: String): Boolean
}