package com.sachin.nutrifyserver.repository

import com.sachin.nutrifyserver.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    fun patchData()
}