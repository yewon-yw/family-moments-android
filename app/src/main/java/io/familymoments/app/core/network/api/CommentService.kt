package io.familymoments.app.core.network.api

import io.familymoments.app.feature.postdetail.model.request.CommentLovesRequest
import io.familymoments.app.feature.postdetail.model.request.PostCommentRequest
import io.familymoments.app.feature.postdetail.model.response.DeleteCommentLovesResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentLovesResponse
import io.familymoments.app.feature.postdetail.model.response.DeleteCommentResponse
import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
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
    ): Response<PostCommentResponse>

    @DELETE("/comments/{index}")
    suspend fun deleteComment(
        @Path("index") index: Int
    ):Response<DeleteCommentResponse>

    @POST("/commentloves")
    suspend fun postCommentLoves(
        @Body commentLovesRequest: CommentLovesRequest
    ):Response<PostCommentLovesResponse>

    @HTTP(method="DELETE", hasBody=true, path="/commentloves")
    suspend fun deleteCommentLoves(
        @Body commentLovesRequest: CommentLovesRequest
    ):Response<DeleteCommentLovesResponse>
}
