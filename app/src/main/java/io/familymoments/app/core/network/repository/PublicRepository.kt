package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.feature.join.model.request.JoinRequest
import io.familymoments.app.feature.join.model.response.CheckEmailResponse
import io.familymoments.app.feature.join.model.response.CheckIdResponse
import io.familymoments.app.feature.join.model.response.JoinResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PublicRepository {
    suspend fun checkId(id: String): Flow<Resource<CheckIdResponse>>
    suspend fun checkEmail(email: String): Flow<Resource<CheckEmailResponse>>
    suspend fun join(profileImg: MultipartBody.Part, joinRequest: JoinRequest): Flow<Resource<JoinResponse>>
}
