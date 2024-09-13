package io.familymoments.app.core.network.repository

import android.net.Uri
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.response.AlbumResult
import io.familymoments.app.core.network.dto.response.ApiResponse
import io.familymoments.app.core.network.dto.response.DeletePostResponse
import io.familymoments.app.core.network.dto.response.GetPostLovesResult
import io.familymoments.app.core.network.dto.response.PostResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PostRepository {
    suspend fun getPosts(familyId: Long): Flow<Resource<ApiResponse<List<PostResult>>>>
    suspend fun loadMorePosts(
        familyId: Long,
        postId: Long
    ): Flow<Resource<ApiResponse<List<PostResult>>>>

    suspend fun getAlbum(familyId: Long): Flow<Resource<ApiResponse<List<AlbumResult>>>>
    suspend fun loadMoreAlbum(
        familyId: Long,
        postId: Long
    ): Flow<Resource<ApiResponse<List<AlbumResult>>>>

    suspend fun getAlbumDetail(postId: Long): Flow<Resource<ApiResponse<List<String>>>>

    suspend fun getPostsByMonth(
        familyId: Long,
        year: Int,
        month: Int
    ): Flow<Resource<ApiResponse<List<String>>>>

    suspend fun getPostsByDay(
        familyId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Flow<Resource<ApiResponse<List<PostResult>>>>

    suspend fun loadMorePostsByDay(
        familyId: Long,
        year: Int,
        month: Int,
        day: Int,
        postId: Long
    ): Flow<Resource<ApiResponse<List<PostResult>>>>

    suspend fun addPost(
        familyId: Long,
        content: String,
        multipartImgs: List<MultipartBody.Part>?
    ): Flow<Resource<ApiResponse<PostResult>>>

    suspend fun getPostDetail(index: Long): Flow<Resource<ApiResponse<PostResult>>>
    suspend fun getPostLoves(index: Long): Flow<Resource<ApiResponse<List<GetPostLovesResult>>>>
    suspend fun postPostLoves(postId: Long): Flow<Resource<ApiResponse<String>>>
    suspend fun deletePostLoves(postId: Long): Flow<Resource<ApiResponse<String>>>
    suspend fun deletePost(index: Long): Flow<Resource<DeletePostResponse>>
    suspend fun editPost(
        index: Long,
        content: String,
        uris:List<Uri>,
        multipartImgs: List<MultipartBody.Part>?
    ):Flow<Resource<ApiResponse<PostResult>>>
    suspend fun reportPost(postId:Long, reason:String, details:String):Flow<Resource<ApiResponse<String>>>
}
