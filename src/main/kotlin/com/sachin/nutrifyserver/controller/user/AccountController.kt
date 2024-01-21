package com.sachin.nutrifyserver.controller.user

import com.sachin.nutrifyserver.entity.Auth
import com.sachin.nutrifyserver.mapper.ResponseMapper
import com.sachin.nutrifyserver.model.BaseError
import com.sachin.nutrifyserver.model.BaseResponse
import com.sachin.nutrifyserver.model.Token
import com.sachin.nutrifyserver.entity.User
import com.sachin.nutrifyserver.service.AuthService
import com.sachin.nutrifyserver.service.UserService
import com.sachin.nutrifyserver.util.Utils
import com.sachin.nutrifyserver.util.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/account")
class AccountController @Autowired constructor(
    private val userService: UserService,
    private val authService: AuthService,
    private val validator: Validator,
    private val mapper: ResponseMapper
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestHeader apiKey: String,
        @RequestBody user: User
    ): ResponseEntity<BaseResponse> {
        checkAPIKey(apiKey)?.let { return mapper.map(it) }
        userService.insertUser(user).let {
            if (it) {
                updateToken(user.userId)
                return mapper.map(
                    BaseResponse(
                        success = true,
                        data = Token(
                            userId = user.userId,
                            authToken = Utils.generateToken(user.userId)
                        )
                    )
                )
            } else {
                return mapper.map(
                    BaseResponse(
                        success = false,
                        error = BaseError("Some error occurred", "")
                    )
                )
            }
        }
    }

    @GetMapping("/login")
    fun login(
        @RequestHeader apiKey: String,
        @RequestParam userId: String
    ): ResponseEntity<BaseResponse> {
        checkAPIKey(apiKey)?.let { return mapper.map(it) }
        userService.getUser(userId)?.let {
            updateToken(it.userId)
            return mapper.map(
                BaseResponse(
                    success = true,
                    data = it
                )
            )
        } ?: mapper.map(
            BaseResponse(
                success = false,
                error = BaseError("User doesn't exist", "")
            )
        )
        return genericError()
    }

    @GetMapping("user")
    fun getUserDetails(
        @RequestHeader apiKey: String,
        @RequestHeader authToken: String,
        @RequestParam currentUserId: String,
        @RequestParam paramsMap: HashMap<String, Any>
    ): ResponseEntity<BaseResponse> {
        checkAPIKey(apiKey)?.let { return mapper.map(it) }
        checkAuthToken(currentUserId, authToken)?.let { return mapper.map(it) }
        filterUserIds(paramsMap)?.let {
            userService.getUsersByIDs(it)?.let { list ->
                mapper.map(
                    BaseResponse(
                        success = true,
                        data = list
                    )
                )
            } ?: mapper.map(
                BaseResponse(
                    success = false,
                    error = BaseError("No users exist", "")
                )
            )

        } ?: mapper.map(
            BaseResponse(
                success = false,
                error = BaseError("Some of the user id is wrong, check again", "")
            )
        )
        return genericError()
    }

    private fun genericError() = ResponseEntity<BaseResponse>(HttpStatus.NOT_FOUND)

    private fun updateToken(userId: String) {
        authService.updateToken(
            Auth(
                userId = userId,
                authToken = Utils.generateToken((userId)),
                lastUpdated = Utils.getCurrentTms()
            )
        )
    }

    private fun filterUserIds(paramsMap: HashMap<String, Any>): List<String>? =
        paramsMap.filter { it.key.startsWith("userId") }.values.toList() as? List<String>

    private fun checkAPIKey(apiKey: String): BaseResponse? =
        if (!validator.validateApiKey(apiKey)) {
            BaseResponse(
                success = false,
                error = BaseError("Invalid api key", "")
            )
        } else {
            null
        }

    private fun checkAuthToken(userId: String, token: String): BaseResponse? =
        if (!validator.validateAuthToken(userId, token)) {
            BaseResponse(
                success = false,
                error = BaseError("Unauthorized", "")
            )
        } else {
            null
        }
}