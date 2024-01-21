package com.sachin.nutrifyserver.controller.user

import com.sachin.nutrifyserver.entity.Auth
import com.sachin.nutrifyserver.mapper.RequestMapper
import com.sachin.nutrifyserver.mapper.ResponseMapper
import com.sachin.nutrifyserver.model.BaseError
import com.sachin.nutrifyserver.model.BaseResponse
import com.sachin.nutrifyserver.model.Token
import com.sachin.nutrifyserver.model.UserRequest
import com.sachin.nutrifyserver.service.AuthService
import com.sachin.nutrifyserver.service.UserService
import com.sachin.nutrifyserver.util.Utils
import com.sachin.nutrifyserver.util.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    private val requestMapper: RequestMapper,
    private val responseMapper: ResponseMapper
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestHeader apiKey: String,
        @RequestBody user: UserRequest
    ): ResponseEntity<BaseResponse> {
        checkAPIKey(apiKey)?.let { return responseMapper.map(it) }
        userService.insertUser(requestMapper.map(user)).let {
            if (it) {
                updateToken(user.phone)
                return responseMapper.map(
                    BaseResponse(
                        success = true,
                        data = Token(
                            userId = user.phone,
                            authToken = Utils.generateToken(user.phone)
                        )
                    )
                )
            } else {
                return responseMapper.map(
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
        checkAPIKey(apiKey)?.let { return responseMapper.map(it) }
        userService.getUser(userId)?.let {
            updateToken(it.phone)
            return responseMapper.map(
                BaseResponse(
                    success = true,
                    data = responseMapper.map(it)
                )
            )
        } ?: responseMapper.map(
            BaseResponse(
                success = false,
                error = BaseError("User doesn't exist", "")
            )
        )
        return genericError()
    }

    @GetMapping("user")
    fun getUser(
        @RequestHeader apiKey: String,
        @RequestHeader authToken: String,
        @RequestParam currentUserId: String,
        @RequestParam paramsMap: HashMap<String, Any>
    ): ResponseEntity<BaseResponse> {
        checkAPIKey(apiKey)?.let { return responseMapper.map(it) }
        checkAuthToken(currentUserId, authToken)?.let { return responseMapper.map(it) }
        filterUserIds(paramsMap)?.let {
            userService.getUsersByIDs(it)?.let { list ->
                responseMapper.map(
                    BaseResponse(
                        success = true,
                        data = responseMapper.map(list)
                    )
                )
            } ?: responseMapper.map(
                BaseResponse(
                    success = false,
                    error = BaseError("No users exist", "")
                )
            )

        } ?: responseMapper.map(
            BaseResponse(
                success = false,
                error = BaseError("Some of the user id is wrong, check again", "")
            )
        )
        return genericError()
    }

    @PutMapping("/update")
    fun putUser(
        @RequestHeader apiKey: String,
        @RequestHeader authToken: String,
        @RequestBody user: UserRequest
    ): ResponseEntity<BaseResponse>{
        checkAPIKey(apiKey)?.let { return responseMapper.map(it) }
        checkAuthToken(user.phone, authToken)?.let { return responseMapper.map(it) }
        userService.updateUser(requestMapper.map(user)).let {
            if (it) {
                return responseMapper.map(
                    BaseResponse(
                        success = true,
                        data = "User updated successfully"
                    )
                )
            } else {
                return responseMapper.map(
                    BaseResponse(
                        success = false,
                        error = BaseError("Some error occurred", "")
                    )
                )
            }
        }
    }
    private fun genericError() = ResponseEntity<BaseResponse>(HttpStatus.NOT_FOUND)

    private fun updateToken(phoneId: String) {
        authService.updateToken(
            Auth(
                userId = phoneId,
                authToken = Utils.generateToken((phoneId)),
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