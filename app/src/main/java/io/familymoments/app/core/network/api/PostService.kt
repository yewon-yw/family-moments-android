package io.familymoments.app.core.network.api

import io.familymoments.app.core.network.dto.response.AddPostResponse
import io.familymoments.app.core.network.dto.response.GetAlbumDetailResponse
import io.familymoments.app.core.network.dto.response.GetAlbumResponse
import io.familymoments.app.core.network.dto.response.GetPostsByMonthResponse
import io.familymoments.app.core.network.dto.response.GetPostsResponse
import io.familymoments.app.core.network.dto.request.PostLovesRequest
import io.familymoments.app.core.network.dto.response.DeletePostLovesResponse
import io.familymoments.app.core.network.dto.response.DeletePostResponse
import io.familymoments.app.core.network.dto.response.GetPostDetailResponse
import io.familymoments.app.core.network.dto.response.GetPostLovesResponse
import io.familymoments.app.core.network.dto.response.PostPostLovesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    suspend fun getPosts(@Query("familyId") familyId: Long): Response<io.familymoments.app.core.network.dto.response.GetPostsResponse>

    @GET("/posts")
    suspend fun loadMorePosts(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<io.familymoments.app.core.network.dto.response.GetPostsResponse>

    @GET("/posts/album")
    suspend fun getAlbum(@Query("familyId") familyId: Long): Response<io.familymoments.app.core.network.dto.response.GetAlbumResponse>

    @GET("/posts/album")
    suspend fun loadMoreAlbum(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<io.familymoments.app.core.network.dto.response.GetAlbumResponse>

    @GET("/posts/album/{postId}")
    suspend fun getAlbumDetail(@Path("postId") postId: Long): Response<io.familymoments.app.core.network.dto.response.GetAlbumDetailResponse>

    @GET("/posts/calendar")
    suspend fun getPostsByMonth(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<io.familymoments.app.core.network.dto.response.GetPostsByMonthResponse>

    @GET("/posts/calendar")
    suspend fun getPostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Response<io.familymoments.app.core.network.dto.response.GetPostsResponse>

    @GET("/posts/calendar")
    suspend fun loadMorePostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("postId") postId: Long
    ): Response<io.familymoments.app.core.network.dto.response.GetPostsResponse>

    @Multipart
    @POST("/posts")
    suspend fun addPost(
        @Query("familyId") familyId: Long,
        @Part("postInfo") postInfo: RequestBody,
        @Part images: List<MultipartBody.Part>?
    ): Response<io.familymoments.app.core.network.dto.response.AddPostResponse>

    @GET("/posts/{index}")
    suspend fun getPostDetail(@Path("index") index: Long): Response<io.familymoments.app.core.network.dto.response.GetPostDetailResponse>

    @GET("/posts/{index}/post-loves")
    suspend fun getPostLoves(@Path("index") index: Long): Response<io.familymoments.app.core.network.dto.response.GetPostLovesResponse>

    @POST("/postloves")
    suspend fun postPostloves(@Body postlovesRequest: io.familymoments.app.core.network.dto.request.PostLovesRequest): Response<io.familymoments.app.core.network.dto.response.PostPostLovesResponse>

    @HTTP(method = "DELETE", hasBody = true, path = "/postloves")
    suspend fun deletePostloves(@Body postlovesRequest: io.familymoments.app.core.network.dto.request.PostLovesRequest): Response<io.familymoments.app.core.network.dto.response.DeletePostLovesResponse>

    @DELETE("/posts/{index}")
    suspend fun deletePost(@Path("index") index: Long): Response<io.familymoments.app.core.network.dto.response.DeletePostResponse>
}
