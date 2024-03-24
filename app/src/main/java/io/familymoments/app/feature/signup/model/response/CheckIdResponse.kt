package io.familymoments.app.feature.signup.model.response

data class CheckIdResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: String,
)
