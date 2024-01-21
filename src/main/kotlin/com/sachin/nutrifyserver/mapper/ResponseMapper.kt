package com.sachin.nutrifyserver.mapper

import com.sachin.nutrifyserver.entity.User
import com.sachin.nutrifyserver.model.BaseResponse
import com.sachin.nutrifyserver.model.UserRequest
import com.sachin.nutrifyserver.util.Utils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ResponseMapper {

    fun map(baseResponse: BaseResponse) = ResponseEntity<BaseResponse>(
        baseResponse,
        if (baseResponse.success)
            HttpStatus.OK
        else
            HttpStatus.BAD_REQUEST
    )

    fun map(user: User) = UserRequest(
        phone = user.phone,
        email = user.email,
        name = user.name,
        password = user.password,
        dob = user.dob,
        encodedImage = Utils.encodeImage(user.profileLink),
        lastActive = user.lastActiveTms
    )

    fun map(list: List<User>): List<UserRequest> =
        list.map { map(it) }
}