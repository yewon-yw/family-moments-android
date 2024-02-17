package io.familymoments.app.core.network.api

import io.familymoments.app.feature.home.model.GetPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/posts")
    suspend fun getPosts(@Query("familyId") familyId: Long): Response<GetPostsResponse>
}
