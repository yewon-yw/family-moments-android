package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.dto.request.CreateFamilyRequest
import io.familymoments.app.core.network.dto.request.SearchFamilyByInviteLinkRequest
import io.familymoments.app.core.network.dto.response.CreateFamilyResponse
import io.familymoments.app.core.network.dto.response.GetFamilyNameResponse
import io.familymoments.app.core.network.dto.response.GetNicknameDdayResponse
import io.familymoments.app.core.network.dto.response.JoinFamilyResponse
import io.familymoments.app.core.network.dto.response.SearchFamilyByInviteLinkResponse
import io.familymoments.app.core.network.dto.response.FamilyInfoResponse
import io.familymoments.app.feature.modifyfamilyInfo.model.ModifyFamilyInfoRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FamilyService {

    @GET("/families/{familyId}/created")
    suspend fun getNicknameDday(@Path("familyId") familyId: Long): Response<GetNicknameDdayResponse>

    @Multipart
    @POST("/families/family")
    suspend fun createFamily(
        @Part representImg: MultipartBody.Part,
        @Part("postFamilyReq") createFamilyRequest: CreateFamilyRequest
    ): Response<CreateFamilyResponse>

    @POST("/families/inviteCode")
    suspend fun searchFamilyByInviteLink(
        @Body searchFamilyByInviteLinkRequest: SearchFamilyByInviteLinkRequest
    ): Response<SearchFamilyByInviteLinkResponse>

    @POST("/families/{familyId}/join")
    suspend fun joinFamily(@Path("familyId") familyId: Long): Response<JoinFamilyResponse>

    @GET("/families/{familyId}")
    suspend fun getFamilyInfo(@Path("familyId") familyId: Long): Response<FamilyInfoResponse>

    @Multipart
    @PATCH("/families/{familyId}/update")
    suspend fun modifyFamilyInfo(
        @Path("familyId") familyId: Long,
        @Part("familyUpdateReq") modifyFamilyInfoRequest: ModifyFamilyInfoRequest,
        @Part representImg: MultipartBody.Part
    ): Response<FamilyInfoResponse>

    @GET("/families/{familyId}/famillyName")
    suspend fun getFamilyName(@Path("familyId") familyId: Long): Response<GetFamilyNameResponse>
}
