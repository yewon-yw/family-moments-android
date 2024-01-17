package io.familymoments.app.repository
import io.familymoments.app.model.join.data.request.JoinRequest
import io.familymoments.app.model.join.data.response.CheckEmailResponse
import io.familymoments.app.model.join.data.response.CheckIdResponse
import io.familymoments.app.model.join.data.response.JoinResponse
import io.familymoments.app.network.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface JoinRepository {
    suspend fun checkId(id:String):Flow<Resource<CheckIdResponse>>
    suspend fun checkEmail(email:String):Flow<Resource<CheckEmailResponse>>
    suspend fun join(profileImg: MultipartBody.Part, joinRequest: JoinRequest):Flow<Resource<JoinResponse>>
}