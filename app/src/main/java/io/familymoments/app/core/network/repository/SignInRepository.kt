package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.request.SignUpRequest
import io.familymoments.app.core.network.dto.response.CheckEmailResponse
import io.familymoments.app.core.network.dto.response.CheckIdResponse
import io.familymoments.app.core.network.dto.response.SignUpResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

// User API 중 토큰이 필요없는 API 들
interface SignInRepository {
    suspend fun checkId(id: String): Flow<Resource<io.familymoments.app.core.network.dto.response.CheckIdResponse>>
    suspend fun checkEmail(email: String): Flow<Resource<io.familymoments.app.core.network.dto.response.CheckEmailResponse>>
    suspend fun executeSignUp(
        profileImg: MultipartBody.Part,
        signUpRequest: io.familymoments.app.core.network.dto.request.SignUpRequest
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.SignUpResponse>>
}
