package io.familymoments.app.repository

interface TokenRepository {
    fun saveAccessToken(token: String)
    fun loadAccessToken(): String
}
