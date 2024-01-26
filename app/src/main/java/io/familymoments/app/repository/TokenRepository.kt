package io.familymoments.app.repository

interface TokenRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun loadAccessToken(): String
    suspend fun saveRefreshToken(token:String)
    suspend fun loadRefreshToken(): String
}
