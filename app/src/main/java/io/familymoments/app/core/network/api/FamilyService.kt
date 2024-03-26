package io.familymoments.app.core.network.api

import io.familymoments.app.feature.creatingfamily.model.CreateFamilyRequest
import io.familymoments.app.feature.creatingfamily.model.CreateFamilyResponse
import io.familymoments.app.feature.home.model.GetNicknameDdayResponse
import io.familymoments.app.feature.joiningfamily.model.SearchFamilyByInviteLinkRequest
import io.familymoments.app.feature.joiningfamily.model.SearchFamilyByInviteLinkResponse
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
}
