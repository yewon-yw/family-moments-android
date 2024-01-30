package io.familymoments.app.model

data class TokenResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = ""
)
