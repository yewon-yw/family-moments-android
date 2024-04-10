package io.familymoments.app.core.network.datasource

import io.familymoments.app.core.network.dto.response.UserProfile
import io.familymoments.app.core.network.dto.response.ProfileEditResult

interface UserInfoPreferencesDataSource {
    suspend fun saveAccessToken(token: String)
    suspend fun loadAccessToken(): String
    suspend fun saveFamilyId(familyId: Long)
    suspend fun loadFamilyId(): Long
    suspend fun saveUserProfile(userProfile: io.familymoments.app.core.network.dto.response.UserProfile)
    suspend fun loadUserProfile(): io.familymoments.app.core.network.dto.response.UserProfile
    suspend fun loadUserProfileImg(): String

    suspend fun resetPreferencesData()
    suspend fun updateUserProfile(profileEditResult: io.familymoments.app.core.network.dto.response.ProfileEditResult)
}
