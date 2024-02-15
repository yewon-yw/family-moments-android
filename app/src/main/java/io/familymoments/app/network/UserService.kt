package io.familymoments.app.network

import io.familymoments.app.model.request.LoginRequest
import io.familymoments.app.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/users/log-in")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("/users/validate")
    suspend fun checkAccessTokenValidation(): Response<Void>
    @POST("/users/reissue")
    suspend fun reissueAccessToken(): Response<Void>
}
