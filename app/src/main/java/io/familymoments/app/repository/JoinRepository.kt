package io.familymoments.app.repository
import io.familymoments.app.model.CheckEmailResponse
import io.familymoments.app.model.CheckIdResponse
import io.familymoments.app.model.JoinInfo
import io.familymoments.app.model.JoinResponse
import io.familymoments.app.network.Resource
import kotlinx.coroutines.flow.Flow

interface JoinRepository {
    suspend fun checkId(id:String):Flow<Resource<CheckIdResponse>>
    suspend fun checkEmail(email:String):Flow<Resource<CheckEmailResponse>>
    suspend fun join(joinInfo: JoinInfo):Flow<Resource<JoinResponse>>
}