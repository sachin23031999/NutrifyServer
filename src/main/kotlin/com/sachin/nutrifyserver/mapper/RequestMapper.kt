package com.sachin.nutrifyserver.mapper

import com.sachin.nutrifyserver.entity.User
import com.sachin.nutrifyserver.model.UserRequest
import com.sachin.nutrifyserver.util.Uploader
import com.sachin.nutrifyserver.util.Utils
import org.springframework.stereotype.Component

@Component
class RequestMapper {

    fun map(request: UserRequest) = User(
        phone = request.phone,
        email = request.email,
        name = request.name,
        password = request.password,
        dob = request.dob,
        profileLink = Uploader.uploadImage(request.encodedImage),
        lastActiveTms = request.lastActive
    )


}