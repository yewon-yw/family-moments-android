package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.dto.request.CreateFamilyRequest
import io.familymoments.app.core.network.dto.response.CreateFamilyResponse
import io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse
import io.familymoments.app.core.network.dto.response.JoinFamilyResponse
import io.familymoments.app.core.network.dto.request.SearchFamilyByInviteLinkRequest
import io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FamilyService {

    @GET("/families/{familyId}/created")
    suspend fun getNicknameDday(@Path("familyId") familyId: Long): Response<io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse>

    @Multipart
    @POST("/families/family")
    suspend fun createFamily(
        @Part representImg: MultipartBody.Part,
        @Part("postFamilyReq") createFamilyRequest: io.familymoments.app.core.network.dto.request.CreateFamilyRequest
    ): Response<io.familymoments.app.core.network.dto.response.CreateFamilyResponse>

    @POST("/families/inviteCode")
    suspend fun searchFamilyByInviteLink(
        @Body searchFamilyByInviteLinkRequest: io.familymoments.app.core.network.dto.request.SearchFamilyByInviteLinkRequest
    ): Response<io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse>

    @POST("/families/{familyId}/join")
    suspend fun joinFamily( @Path("familyId") familyId: Long ):Response<io.familymoments.app.core.network.dto.response.JoinFamilyResponse>
}
