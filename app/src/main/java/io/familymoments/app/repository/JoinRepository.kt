package io.familymoments.app.repository
import io.familymoments.app.model.CheckEmailResponse
import io.familymoments.app.model.CheckIdResponse
import io.familymoments.app.model.JoinRequest
import io.familymoments.app.model.JoinResponse
import io.familymoments.app.network.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface JoinRepository {
    suspend fun checkId(id:String):Flow<Resource<CheckIdResponse>>
    suspend fun checkEmail(email:String):Flow<Resource<CheckEmailResponse>>
    suspend fun join(profileImg: MultipartBody.Part, joinRequest: JoinRequest):Flow<Resource<JoinResponse>>
}