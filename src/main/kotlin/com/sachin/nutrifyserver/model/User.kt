package com.sachin.nutrifyserver.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") val userId: String,
    @Column(name = "phone") val phone: String,
    @Column(name = "email") val email: String,
    @Column(name = "name") val name: String,
    @Column(name = "password") val password: String,
    @Column(name = "token") val token: String,
    @Column(name = "dob") val dob: String,
    @Column(name = "account_status") var accountStatus: AccountStatus,
    @Column(name = "image_link") val imageLink: String,
    @Column(name = "creation_tms") val creationTms: String,
    @Column(name = "last_active_tms") val lastActiveTms: String
)