package com.sachin.nutrifyserver.service.impl

import com.sachin.nutrifyserver.model.AccountStatus
import com.sachin.nutrifyserver.entity.User
import com.sachin.nutrifyserver.repository.UserRepository
import com.sachin.nutrifyserver.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository
) : UserService {

    val bgScope = CoroutineScope(Dispatchers.IO + Job())
    override fun insertUser(user: User): Boolean =
        try {
            userRepository.save(user)
            true
        } catch (_: Exception) {
            false
        }

    override fun getUser(userId: String): User? =
        try {
            userRepository.findById(userId).get()
        } catch (_: Exception) {
            null
        }

    override fun getAllUser(): List<User>? =
        try {
            userRepository.findAll()
        } catch (_: Exception) {
            null
        }

    override fun getUsersByIDs(userIds: List<String>): List<User>? =
        userRepository.findAllById(userIds)

    override fun updateUser(user: User): Boolean {
        try {
            userRepository.apply {
                return if (existsById(user.userId)) {
                    save(user)
                    true
                } else {
                    false
                }
            }
        } catch (_: Exception) {
            return false
        }
    }

    override fun deleteUser(userId: String) {
        userRepository.deleteById(userId)
    }

    override fun archiveUser(userId: String): Boolean {
        try {
            userRepository.findById(userId).get().let { user ->
                user.accountStatus = AccountStatus.ARCHIVED
                userRepository.save(user)
                return true
            }
        } catch (_: Exception) {
            return false
        }
    }

    override fun unarchiveUser(userId: String): Boolean {
        try {
            userRepository.findById(userId).get().let { user ->
                user.accountStatus = AccountStatus.ACTIVE
                userRepository.save(user)
                return true
            }
        } catch (_: Exception) {
            return false
        }
    }
}