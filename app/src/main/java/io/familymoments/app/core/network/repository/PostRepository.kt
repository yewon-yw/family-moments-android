package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.response.AddPostResponse
import io.familymoments.app.core.network.dto.response.GetAlbumDetailResponse
import io.familymoments.app.core.network.dto.response.GetAlbumResponse
import io.familymoments.app.core.network.dto.response.GetPostsByMonthResponse
import io.familymoments.app.core.network.dto.response.GetPostsResponse
import io.familymoments.app.core.network.dto.response.DeletePostLovesResponse
import io.familymoments.app.core.network.dto.response.DeletePostResponse
import io.familymoments.app.core.network.dto.response.GetPostDetailResponse
import io.familymoments.app.core.network.dto.response.GetPostLovesResponse
import io.familymoments.app.core.network.dto.response.PostPostLovesResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PostRepository {
    suspend fun getPosts(familyId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostsResponse>>
    suspend fun loadMorePosts(familyId: Long, postId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostsResponse>>

    suspend fun getAlbum(familyId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetAlbumResponse>>
    suspend fun loadMoreAlbum(familyId: Long, postId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetAlbumResponse>>
    suspend fun getAlbumDetail(postId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetAlbumDetailResponse>>

    suspend fun getPostsByMonth(familyId: Long, year: Int, month: Int): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostsByMonthResponse>>
    suspend fun getPostsByDay(familyId: Long, year: Int, month: Int, day: Int): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostsResponse>>
    suspend fun loadMorePostsByDay(
        familyId: Long,
        year: Int,
        month: Int,
        day: Int,
        postId: Long
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostsResponse>>

    suspend fun addPost(
        familyId: Long,
        content: String,
        uriList: List<MultipartBody.Part>?
    ): Flow<Resource<io.familymoments.app.core.network.dto.response.AddPostResponse>>

    suspend fun getPostDetail(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostDetailResponse>>
    suspend fun getPostLoves(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetPostLovesResponse>>
    suspend fun postPostLoves(postId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.PostPostLovesResponse>>
    suspend fun deletePostLoves(postId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeletePostLovesResponse>>
    suspend fun deletePost(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeletePostResponse>>
}
