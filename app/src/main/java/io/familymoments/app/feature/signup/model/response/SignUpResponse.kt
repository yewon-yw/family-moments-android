package io.familymoments.app.feature.signup.model.response

data class SignUpResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: SignUpResult
)

