package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.repository.PublicRepository
import io.familymoments.app.feature.join.model.request.CheckEmailRequest
import io.familymoments.app.feature.join.model.request.CheckIdRequest
import io.familymoments.app.feature.join.model.request.JoinRequest
import io.familymoments.app.feature.join.model.response.CheckEmailResponse
import io.familymoments.app.feature.join.model.response.CheckIdResponse
import io.familymoments.app.feature.join.model.response.JoinResponse
import io.familymoments.app.core.network.api.PublicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class PublicRepositoryImpl(
    private val joinService: PublicService
):PublicRepository {
    override suspend fun checkId(id: String): Flow<Resource<CheckIdResponse>> {
        return flow {
            emit(Resource.Loading)
            val result = joinService.checkId(CheckIdRequest(id))

            if (result.isSuccess) {
                emit(Resource.Success(result))
            } else {
                throw Throwable(result.message)
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun checkEmail(email: String): Flow<Resource<CheckEmailResponse>> {
        return flow {
            emit(Resource.Loading)

            val result = joinService.checkEmail(CheckEmailRequest(email))

            if (result.isSuccess) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Fail(Throwable(result.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun join(profileImg: MultipartBody.Part, joinRequest: JoinRequest): Flow<Resource<JoinResponse>> {
        return flow {
            emit(Resource.Loading)

            val result = joinService.join(profileImg, joinRequest)

            if (result.isSuccess) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Fail(Throwable(result.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }
}
