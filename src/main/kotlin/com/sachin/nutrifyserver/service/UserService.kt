package com.sachin.nutrifyserver.service

import com.sachin.nutrifyserver.entity.User

interface UserService {

    fun insertUser(user: User): Boolean

    fun getUser(userId: String): User?

    fun getUsersByIDs(userIds: List<String>): List<User>?
    fun getAllUser(): List<User>?

    fun updateUser(user: User): Boolean

    fun patchUser(vararg fields: String): Boolean
    fun deleteUser(userId: String)

    fun archiveUser(userId: String): Boolean

    fun unarchiveUser(userId: String): Boolean
}