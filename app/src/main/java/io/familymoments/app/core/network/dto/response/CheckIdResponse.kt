package io.familymoments.app.core.network.dto.response

data class CheckIdResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: String,
)
