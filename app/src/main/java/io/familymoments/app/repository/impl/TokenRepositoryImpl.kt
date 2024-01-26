package io.familymoments.app.repository.impl

import android.content.Context
import io.familymoments.app.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(context: Context) : TokenRepository {

    private val preferences = context.getSharedPreferences(PREFERENCE_TOKEN_KEY, Context.MODE_PRIVATE)
    override suspend fun saveAccessToken(token: String) {
        with(preferences.edit()) {
            putString(ACCESS_TOKEN_KEY, token)
            apply()
        }
    }

    override suspend fun loadAccessToken(): String {
        return preferences.getString(ACCESS_TOKEN_KEY, DEFAULT_TOKEN_VALUE) ?: throw IllegalStateException(
            KEY_NOT_EXIST_ERROR
        )
    }

    override suspend fun saveRefreshToken(token: String) {
        with(preferences.edit()) {
            putString(REFRESH_TOKEN_KEY, token)
            apply()
        }
    }

    override suspend fun loadRefreshToken(): String {
        return preferences.getString(REFRESH_TOKEN_KEY, DEFAULT_TOKEN_VALUE) ?: throw IllegalStateException(
            KEY_NOT_EXIST_ERROR
        )
    }

    companion object {
        private const val PREFERENCE_TOKEN_KEY = "token"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val KEY_NOT_EXIST_ERROR = "해당 키 값이 존재하지 않습니다."
        private const val DEFAULT_TOKEN_VALUE = ""
    }
}
