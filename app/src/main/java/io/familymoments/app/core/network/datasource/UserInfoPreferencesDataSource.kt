package io.familymoments.app.core.network.datasource

interface UserInfoPreferencesDataSource {
    suspend fun saveAccessToken(token: String)
    suspend fun loadAccessToken(): String
    suspend fun saveFamilyId(familyId:Long)
    suspend fun loadFamilyId():Long
}
