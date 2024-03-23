package io.familymoments.app.core.network.api

import io.familymoments.app.feature.postdetail.model.request.PostCommentRequest
import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentService {
    @GET("/comments")
    suspend fun getCommentsByPostIndex(
        @Query("postId") postId: Int
    ): Response<GetCommentsByPostIndexResponse>

    @POST("/comments")
    suspend fun postComment(
        @Query("postId") postId: Int,
        @Body postCommentReq: PostCommentRequest
    ):Response<PostCommentResponse>
}
