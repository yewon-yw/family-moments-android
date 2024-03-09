package io.familymoments.app.core.network.api

import io.familymoments.app.feature.album.model.GetAlbumResponse
import io.familymoments.app.feature.calendar.model.GetPostsByMonthResponse
import io.familymoments.app.feature.home.model.GetPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/posts")
    suspend fun getPosts(@Query("familyId") familyId: Long): Response<GetPostsResponse>

    @GET("/posts")
    suspend fun loadMorePosts(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<GetPostsResponse>

    @GET("/posts/album")
    suspend fun getAlbum(@Query("familyId") familyId: Long): Response<GetAlbumResponse>

    @GET("/posts/album")
    suspend fun loadMoreAlbum(
        @Query("familyId") familyId: Long,
        @Query("postId") postId: Long
    ): Response<GetAlbumResponse>

    @GET("/posts/calendar")
    suspend fun getPostsByMonth(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<GetPostsByMonthResponse>

    @GET("/posts/calendar")
    suspend fun getPostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Response<GetPostsResponse>

    @GET("/posts/calendar")
    suspend fun loadMorePostsByDay(
        @Query("familyId") familyId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("postId") postId: Long
    ): Response<GetPostsResponse>
}
