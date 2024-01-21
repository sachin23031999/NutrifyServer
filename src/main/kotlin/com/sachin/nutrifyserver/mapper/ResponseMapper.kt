package com.sachin.nutrifyserver.mapper

import com.sachin.nutrifyserver.model.BaseResponse
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
}