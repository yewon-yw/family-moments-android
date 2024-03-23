package io.familymoments.app.core.network.api

import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {
    @GET("/comments")
    suspend fun getCommentsByPostIndex(
        @Query("postId") postId: Int
    ): Response<GetCommentsByPostIndexResponse>
}
