package com.sachin.nutrifyserver.repository

import com.sachin.nutrifyserver.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String>