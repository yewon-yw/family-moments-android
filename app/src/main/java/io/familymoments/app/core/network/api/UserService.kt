package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.model.UserProfileResponse
import io.familymoments.app.feature.login.model.request.LoginRequest
import io.familymoments.app.feature.login.model.response.LoginResponse
import io.familymoments.app.feature.modifypassword.model.request.ModifyPasswordRequest
import io.familymoments.app.feature.modifypassword.model.response.ModifyPasswordResponse
import io.familymoments.app.feature.mypage.model.response.LogoutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/users/log-in")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/users/reissue")
    suspend fun reissueAccessToken(): Response<Void>
    @GET("/users/profile")
    suspend fun loadUserProfile(
        @Query("familyId") familyId:Long? = null
    ): Response<UserProfileResponse>

    @PATCH("/users/modify-pwd")
    suspend fun modifyPassword(
        @Body modifyPasswordRequest: ModifyPasswordRequest
    ): Response<ModifyPasswordResponse>

    @POST("/users/log-out")
    suspend fun logoutUser(): Response<LogoutResponse>

    @GET("/users")
    suspend fun searchMember(
        @Query("keyword") keyword: String,
        @Query("familyId") familyId: Long? = null
    ): Response<SearchMemberResponse>
}
