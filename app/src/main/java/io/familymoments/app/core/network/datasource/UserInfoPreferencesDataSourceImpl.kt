package io.familymoments.app.core.network.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class UserInfoPreferencesDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
):UserInfoPreferencesDataSource {
    override suspend fun saveAccessToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN_KEY, token)
            apply()
        }
    }

    override suspend fun loadAccessToken(): String {
        return sharedPreferences.getString(
            ACCESS_TOKEN_KEY,
            DEFAULT_TOKEN_VALUE
        ) ?: throw IllegalStateException(
            KEY_NOT_EXIST_ERROR
        )
    }

    override suspend fun saveFamilyId(familyId: Long) {
        with(sharedPreferences.edit()) {
            putLong(FAMILY_ID_KEY, familyId)
            apply()
        }
    }

    override suspend fun loadFamilyId():Long {
        return sharedPreferences.getLong(
            FAMILY_ID_KEY,
            DEFAULT_FAMILY_ID
        )
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val KEY_NOT_EXIST_ERROR = "액세스 토큰이 존재하지 않습니다."
        private const val DEFAULT_TOKEN_VALUE = ""
        private const val FAMILY_ID_KEY = "family_id"
        private const val DEFAULT_FAMILY_ID = -1L
    }
}
