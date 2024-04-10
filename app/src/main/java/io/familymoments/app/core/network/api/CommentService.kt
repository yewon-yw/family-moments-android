package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.dto.request.CommentLovesRequest
import io.familymoments.app.core.network.dto.request.PostCommentRequest
import io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse
import io.familymoments.app.core.network.dto.response.DeleteCommentResponse
import io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse
import io.familymoments.app.core.network.dto.response.PostCommentLovesResponse
import io.familymoments.app.core.network.dto.response.PostCommentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {
    @GET("/comments")
    suspend fun getPostComments(
        @Query("postId") postId: Long
    ): Response<io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse>

    @Multipart
    @POST("/comments")
    suspend fun postComment(
        @Query("postId") postId: Long,
        @Part("postCommentReq") postCommentReq: io.familymoments.app.core.network.dto.request.PostCommentRequest
    ): Response<io.familymoments.app.core.network.dto.response.PostCommentResponse>

    @DELETE("/comments/{index}")
    suspend fun deleteComment(
        @Path("index") index: Long
    ): Response<io.familymoments.app.core.network.dto.response.DeleteCommentResponse>

    @POST("/commentloves")
    suspend fun postCommentLoves(
        @Body commentLovesRequest: io.familymoments.app.core.network.dto.request.CommentLovesRequest
    ): Response<io.familymoments.app.core.network.dto.response.PostCommentLovesResponse>

    @HTTP(method = "DELETE", hasBody = true, path = "/commentloves")
    suspend fun deleteCommentLoves(
        @Body commentLovesRequest: io.familymoments.app.core.network.dto.request.CommentLovesRequest
    ): Response<io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse>
}
