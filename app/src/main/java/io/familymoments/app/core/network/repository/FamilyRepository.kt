package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.request.CreateFamilyRequest
import io.familymoments.app.core.network.dto.response.CreateFamilyResponse
import io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse
import io.familymoments.app.core.network.dto.response.JoinFamilyResponse
import io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FamilyRepository {
    suspend fun getNicknameDday(familyId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse>>

    suspend fun createFamily(
        representImg: MultipartBody.Part,
        createFamilyRequest: io.familymoments.app.core.network.dto.request.CreateFamilyRequest
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.CreateFamilyResponse>>

    suspend fun searchFamilyByInviteLink(
        inviteLink: String
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse>>

    suspend fun joinFamily(familyId: Long):Flow<Resource<io.familymoments.app.core.network.dto.response.JoinFamilyResponse>>
}
