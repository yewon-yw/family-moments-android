package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.dto.request.AddPostRequest
import io.familymoments.app.core.network.dto.request.EditPostRequest
import io.familymoments.app.core.network.dto.request.PostLovesRequest
import io.familymoments.app.core.network.dto.request.ReportRequest
import io.familymoments.app.core.network.dto.response.AlbumResult
import io.familymoments.app.core.network.dto.response.ApiResponse
import io.familymoments.app.core.network.dto.response.DeletePostResponse
import io.familymoments.app.core.network.dto.response.GetPostLovesResult
import io.familymoments.app.core.network.dto.response.PostResult
import okhttp3.MultipartBody
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

interface PostService {
    @GET("/posts")
    suspend fun getPosts(@Query("familyId") familyId: Long): Response<ApiResponse<List<PostResult>>>

    @GET("/posts")
    suspend fun loadMorePosts(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<ApiResponse<List<PostResult>>>

    @GET("/posts/album")
    suspend fun getAlbum(@Query("familyId") familyId: Long): Response<ApiResponse<List<AlbumResult>>>

    @GET("/posts/album")
    suspend fun loadMoreAlbum(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<ApiResponse<List<AlbumResult>>>

    @GET("/posts/album/{postId}")
    suspend fun getAlbumDetail(@Path("postId") postId: Long): Response<ApiResponse<List<String>>>

    @GET("/posts/calendar")
    suspend fun getPostsByMonth(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<ApiResponse<List<String>>>

    @GET("/posts/calendar")
    suspend fun getPostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Response<ApiResponse<List<PostResult>>>

    @GET("/posts/calendar")
    suspend fun loadMorePostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("postId") postId: Long
    ): Response<ApiResponse<List<PostResult>>>

    @Multipart
    @POST("/posts")
    suspend fun addPost(
        @Query("familyId") familyId: Long,
        @Part("postInfo") postInfo: AddPostRequest,
        @Part images: List<MultipartBody.Part>?
    ): Response<ApiResponse<PostResult>>

    @GET("/posts/{index}")
    suspend fun getPostDetail(@Path("index") index: Long): Response<ApiResponse<PostResult>>

    @GET("/posts/{index}/post-loves")
    suspend fun getPostLoves(@Path("index") index: Long): Response<ApiResponse<List<GetPostLovesResult>>>

    @POST("/postloves")
    suspend fun postPostloves(@Body postlovesRequest: PostLovesRequest): Response<ApiResponse<String>>

    @HTTP(method = "DELETE", hasBody = true, path = "/postloves")
    suspend fun deletePostloves(@Body postlovesRequest: PostLovesRequest): Response<ApiResponse<String>>

    @DELETE("/posts/{index}")
    suspend fun deletePost(@Path("index") index: Long): Response<DeletePostResponse>

    @Multipart
    @POST("/posts/{index}/edit")
    suspend fun editPost(
        @Path("index") index:Long,
        @Part("postInfo") postInfo: EditPostRequest,
        @Part newImgs: List<MultipartBody.Part>?
    ):Response<ApiResponse<PostResult>>

    @POST("/posts/report/{postId}")
    suspend fun reportPost(
        @Path(value = "postId") postId: Long,
        @Body reportRequest: ReportRequest
    ): Response<ApiResponse<String>>
}
