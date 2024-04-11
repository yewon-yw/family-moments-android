package io.familymoments.app.core.network

sealed class AuthErrorResponse : Throwable() {
    data object RefreshTokenExpiration : AuthErrorResponse()
}
