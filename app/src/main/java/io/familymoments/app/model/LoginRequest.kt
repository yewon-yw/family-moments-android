package io.familymoments.app.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)