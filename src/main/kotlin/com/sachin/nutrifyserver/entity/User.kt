package com.sachin.nutrifyserver.entity

import com.sachin.nutrifyserver.model.AccountStatus
import com.sachin.nutrifyserver.util.Utils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @Column(name = "phone") var phone: String,

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") val userId: String? = null,

    @Column(name = "email") var email: String,
    @Column(name = "name") var name: String,
    @Column(name = "password") var password: String,
    @Column(name = "dob") val dob: String,
    @Column(name = "account_status") var accountStatus: AccountStatus = AccountStatus.ACTIVE,
    @Column(name = "profile_link") var profileLink: String,
    @Column(name = "creation_tms") val creationTms: String = Utils.getCurrentTms(),
    @Column(name = "last_active_tms") val lastActiveTms: String //TODO
)