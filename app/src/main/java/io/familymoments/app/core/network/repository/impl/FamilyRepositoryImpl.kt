package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.HttpResponse
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.FamilyService
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.dto.request.CreateFamilyRequest
import io.familymoments.app.core.network.dto.response.CreateFamilyResponse
import io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse
import io.familymoments.app.core.network.dto.response.JoinFamilyResponse
import io.familymoments.app.core.network.dto.request.SearchFamilyByInviteLinkRequest
import io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyService: FamilyService
) : FamilyRepository {
    override suspend fun getNicknameDday(familyId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = familyService.getNicknameDday(familyId)
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun createFamily(
        representImg: MultipartBody.Part,
        createFamilyRequest: io.familymoments.app.core.network.dto.request.CreateFamilyRequest
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.CreateFamilyResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = familyService.createFamily(representImg, createFamilyRequest)
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.CreateFamilyResponse()

            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

    override suspend fun searchFamilyByInviteLink(inviteLink: String): Flow<Resource<io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = familyService.searchFamilyByInviteLink(
                io.familymoments.app.core.network.dto.request.SearchFamilyByInviteLinkRequest(
                    inviteLink
                )
            )
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse()

            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

    override suspend fun joinFamily(familyId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.JoinFamilyResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = familyService.joinFamily(familyId)
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.JoinFamilyResponse()

            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

}
