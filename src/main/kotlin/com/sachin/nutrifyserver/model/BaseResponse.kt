package com.sachin.nutrifyserver.model

data class BaseResponse(
    val success: Boolean,
    val data: Any? = null,
    val error: BaseError? = null
)