package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.feature.signup.model.request.SignUpRequest
import io.familymoments.app.feature.signup.model.response.CheckEmailResponse
import io.familymoments.app.feature.signup.model.response.CheckIdResponse
import io.familymoments.app.feature.signup.model.response.SignUpResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

// User API 중 토큰이 필요없는 API 들
interface SignInRepository {
    suspend fun checkId(id:String): Flow<Resource<CheckIdResponse>>
    suspend fun checkEmail(email:String): Flow<Resource<CheckEmailResponse>>
    suspend fun executeSignUp(profileImg: MultipartBody.Part, signUpRequest: SignUpRequest): Flow<Resource<SignUpResponse>>
}
