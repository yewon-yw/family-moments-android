package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.response.UserProfileResponse
import io.familymoments.app.core.network.dto.response.SearchMemberResponse
import io.familymoments.app.core.network.dto.response.LoginResponse
import io.familymoments.app.core.network.dto.request.ModifyPasswordRequest
import io.familymoments.app.core.network.dto.response.ModifyPasswordResponse
import io.familymoments.app.core.network.dto.response.LogoutResponse
import io.familymoments.app.core.network.dto.request.ProfileEditRequest
import io.familymoments.app.core.network.dto.response.ProfileEditResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

// User API 중 토큰이 필요한 API 들
interface UserRepository {
    suspend fun loginUser(username: String, password: String): Flow<Resource<io.familymoments.app.core.network.dto.response.LoginResponse>>
    suspend fun reissueAccessToken(): Flow<Resource<Unit>>
    suspend fun loadUserProfile(familyId: Long?): Flow<Resource<io.familymoments.app.core.network.dto.response.UserProfileResponse>>
    suspend fun modifyPassword(modifyPasswordRequest: io.familymoments.app.core.network.dto.request.ModifyPasswordRequest): Flow<Resource<io.familymoments.app.core.network.dto.response.ModifyPasswordResponse>>
    suspend fun logoutUser(): Flow<Resource<io.familymoments.app.core.network.dto.response.LogoutResponse>>
    suspend fun searchMember(keyword: String, newFamily: Boolean): Flow<Resource<io.familymoments.app.core.network.dto.response.SearchMemberResponse>>
    suspend fun editUserProfile(
        profileEditRequest: io.familymoments.app.core.network.dto.request.ProfileEditRequest,
        profileImg: MultipartBody.Part
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.ProfileEditResponse>>
}
