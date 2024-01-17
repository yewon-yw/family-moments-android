package io.familymoments.app.network

import io.familymoments.app.model.join.data.request.CheckEmailRequest
import io.familymoments.app.model.join.data.request.CheckIdRequest
import io.familymoments.app.model.join.data.request.JoinRequest
import io.familymoments.app.model.join.data.response.CheckEmailResponse
import io.familymoments.app.model.join.data.response.CheckIdResponse
import io.familymoments.app.model.join.data.response.JoinResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface JoinService {
    @POST("/users/check-id")
    suspend fun checkId(@Body checkIdRequest: CheckIdRequest): CheckIdResponse

    @POST("/users/check-email")
    suspend fun checkEmail(@Body checkEmailRequest: CheckEmailRequest): CheckEmailResponse

    @Multipart
    @POST("/users/sign-up")
    suspend fun join(@Part profileImg:MultipartBody.Part,
                     @Part("newUser") joinRequest: JoinRequest
    ): JoinResponse
}