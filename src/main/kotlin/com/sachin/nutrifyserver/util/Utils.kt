package com.sachin.nutrifyserver.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun generateToken(userId: String): String {
        val tokenData = "userId:$userId"
        val encodedBytes = Base64.getEncoder().encode(tokenData.toByteArray())
        return String(encodedBytes)
    }

    fun getCurrentTms(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date())
    }
}