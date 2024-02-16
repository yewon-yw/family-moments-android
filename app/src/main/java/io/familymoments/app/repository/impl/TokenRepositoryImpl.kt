package io.familymoments.app.repository.impl

import android.content.SharedPreferences
import io.familymoments.app.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : TokenRepository {

    override suspend fun saveAccessToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN_KEY, token)
            apply()
        }
    }

    override suspend fun loadAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, DEFAULT_TOKEN_VALUE) ?: throw IllegalStateException(
            KEY_NOT_EXIST_ERROR
        )
    }

    override suspend fun saveRefreshToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(REFRESH_TOKEN_KEY, token)
            apply()
        }
    }

    override suspend fun loadRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, DEFAULT_TOKEN_VALUE) ?: throw IllegalStateException(
            KEY_NOT_EXIST_ERROR
        )
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val KEY_NOT_EXIST_ERROR = "해당 키 값이 존재하지 않습니다."
        private const val DEFAULT_TOKEN_VALUE = ""
    }
}
