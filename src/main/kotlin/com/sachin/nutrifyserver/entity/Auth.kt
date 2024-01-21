package com.sachin.nutrifyserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Auth(
    @Id
    @Column(name = "user_id") val userId: String,
    @Column(name = "auth_token") val authToken: String,
    @Column(name = "last_updated") val lastUpdated: String
)