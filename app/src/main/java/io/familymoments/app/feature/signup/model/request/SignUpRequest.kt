package io.familymoments.app.feature.signup.model.request

data class SignUpRequest(
    val id: String,
    val passwordA: String,
    val passwordB: String,
    val name: String,
    val email: String,
    val strBirthDate: String,
    val nickname: String
)
