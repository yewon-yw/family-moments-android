package io.familymoments.app.repository.impl

import io.familymoments.app.model.CheckEmailRequest
import io.familymoments.app.model.CheckEmailResponse
import io.familymoments.app.model.CheckIdRequest
import io.familymoments.app.model.CheckIdResponse
import io.familymoments.app.model.JoinInfo
import io.familymoments.app.model.JoinResponse
import io.familymoments.app.network.JoinService
import io.familymoments.app.network.Resource
import io.familymoments.app.repository.JoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinRepositoryImpl @Inject constructor(
    private val joinService: JoinService
) : JoinRepository {
    override suspend fun checkId(id: String): Flow<Resource<CheckIdResponse>> {
        return flow {

            val result = joinService.checkId(CheckIdRequest(id))

            if (result.isSuccess) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Fail(Throwable(result.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun checkEmail(email: String): Flow<Resource<CheckEmailResponse>> {
        return flow {

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

    override suspend fun join(joinInfo: JoinInfo): Flow<Resource<JoinResponse>> {
        TODO("Not yet implemented")
    }
}