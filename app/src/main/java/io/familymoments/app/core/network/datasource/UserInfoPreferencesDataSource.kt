package io.familymoments.app.core.network.datasource

import io.familymoments.app.core.network.model.UserProfile

interface UserInfoPreferencesDataSource {
    suspend fun saveAccessToken(token: String)
    suspend fun loadAccessToken(): String
    suspend fun saveFamilyId(familyId:Long)
    suspend fun loadFamilyId():Long
    suspend fun saveUserProfile(userProfile: UserProfile)
    suspend fun loadUserProfile():UserProfile
}
