package io.familymoments.app.core.network.dto.response

data class LogoutResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = ""
)
