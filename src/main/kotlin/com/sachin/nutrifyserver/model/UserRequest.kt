package com.sachin.nutrifyserver.model

data class UserRequest(
    val phone: String,
    val email: String,
    val name: String,
    val password: String,
    val dob: String,
    val encodedImage: String,
    val lastActive: String
)