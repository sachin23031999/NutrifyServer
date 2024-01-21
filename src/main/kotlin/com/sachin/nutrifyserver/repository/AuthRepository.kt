package com.sachin.nutrifyserver.repository

import com.sachin.nutrifyserver.entity.Auth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository: JpaRepository<Auth, String>