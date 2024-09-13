package io.familymoments.app.core.network.repository.impl

import android.net.Uri
import io.familymoments.app.core.network.HttpResponse
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.PostService
import io.familymoments.app.core.network.dto.request.AddPostRequest
import io.familymoments.app.core.network.dto.request.EditPostRequest
import io.familymoments.app.core.network.dto.request.PostLovesRequest
import io.familymoments.app.core.network.dto.request.ReportRequest
import io.familymoments.app.core.network.dto.response.AlbumResult
import io.familymoments.app.core.network.dto.response.ApiResponse
import io.familymoments.app.core.network.dto.response.DeletePostResponse
import io.familymoments.app.core.network.dto.response.GetPostLovesResult
import io.familymoments.app.core.network.dto.response.PostResult
import io.familymoments.app.core.network.dto.response.getResourceFlow
import io.familymoments.app.core.network.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService
) : PostRepository {
    override suspend fun getPosts(familyId: Long): Flow<Resource<ApiResponse<List<PostResult>>>> {
        val response = postService.getPosts(familyId)
        return getResourceFlow(response)
    }

    override suspend fun loadMorePosts(
        familyId: Long,
        postId: Long
    ): Flow<Resource<ApiResponse<List<PostResult>>>> {
        val response = postService.loadMorePosts(familyId, postId)
        val responseBody = response.body()
        return flow {
            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody != null && responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else if (responseBody?.code != 404) {
                    // 새로운 Post가 없으면 404 : post가 존재하지 않습니다 에러 발생
                    // 새로운 Post가 없으면 더 이상 로드하지 않으면 되니까, 굳이 flow를 emit하여 UiState를 업데이트할 필요가 없음
                    emit(Resource.Fail(Throwable(responseBody?.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

    override suspend fun getAlbum(familyId: Long): Flow<Resource<ApiResponse<List<AlbumResult>>>> {
        val response = postService.getAlbum(familyId)
        return getResourceFlow(response)
    }

    override suspend fun loadMoreAlbum(
        familyId: Long,
        postId: Long
    ): Flow<Resource<ApiResponse<List<AlbumResult>>>> {
        val response = postService.loadMoreAlbum(familyId, postId)
        val responseBody = response.body()
        return flow {
            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody != null && responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else if (responseBody?.code != 404) {
                    // 새로운 Post가 없으면 404 : post가 존재하지 않습니다 에러 발생
                    // 새로운 Post가 없으면 더 이상 로드하지 않으면 되니까, 굳이 flow를 emit하여 UiState를 업데이트할 필요가 없음
                    emit(Resource.Fail(Throwable(responseBody?.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

    override suspend fun getAlbumDetail(postId: Long): Flow<Resource<ApiResponse<List<String>>>> {
        val response = postService.getAlbumDetail(postId)
        return getResourceFlow(response)
    }

    override suspend fun getPostsByMonth(
        familyId: Long,
        year: Int,
        month: Int
    ): Flow<Resource<ApiResponse<List<String>>>> {
        val response = postService.getPostsByMonth(familyId, year, month)
        return getResourceFlow(response)
    }

    override suspend fun getPostsByDay(
        familyId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Flow<Resource<ApiResponse<List<PostResult>>>> {
        val response = postService.getPostsByDay(familyId, year, month, day)
        return getResourceFlow(response)
    }

    override suspend fun loadMorePostsByDay(
        familyId: Long,
        year: Int,
        month: Int,
        day: Int,
        postId: Long
    ): Flow<Resource<ApiResponse<List<PostResult>>>> {
        val response = postService.loadMorePostsByDay(familyId, year, month, day, postId)
        val responseBody = response.body()
        return flow {
            if (response.code() == HttpResponse.SUCCESS) {
                if (responseBody != null && responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else if (responseBody?.code != 404) {
                    // 새로운 Post가 없으면 404 : post가 존재하지 않습니다 에러 발생
                    // 새로운 Post가 없으면 더 이상 로드하지 않으면 되니까, 굳이 flow를 emit하여 UiState를 업데이트할 필요가 없음
                    emit(Resource.Fail(Throwable(responseBody?.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }
    }

    override suspend fun addPost(
        familyId: Long,
        content: String,
        multipartImgs: List<MultipartBody.Part>?
    ): Flow<Resource<ApiResponse<PostResult>>> {
        val response = postService.addPost(familyId, AddPostRequest(content), multipartImgs)
        return getResourceFlow(response)
    }

    override suspend fun getPostDetail(index: Long): Flow<Resource<ApiResponse<PostResult>>> {
        val response = postService.getPostDetail(index)
        return getResourceFlow(response)
    }

    override suspend fun getPostLoves(index: Long): Flow<Resource<ApiResponse<List<GetPostLovesResult>>>> {
        val response = postService.getPostLoves(index)
        return getResourceFlow(response)
    }

    override suspend fun postPostLoves(postId: Long): Flow<Resource<ApiResponse<String>>> {
        val response = postService.postPostloves(PostLovesRequest(postId))
        return getResourceFlow(response)
    }

    override suspend fun deletePostLoves(postId: Long): Flow<Resource<ApiResponse<String>>> {
        val response = postService.deletePostloves(PostLovesRequest(postId))
        return getResourceFlow(response)
    }

    override suspend fun deletePost(index: Long): Flow<Resource<DeletePostResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = postService.deletePost(index)
            val responseBody = response.body() ?: DeletePostResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun editPost(
        index: Long,
        content: String,
        uris: List<Uri>,
        multipartImgs: List<MultipartBody.Part>?
    ): Flow<Resource<ApiResponse<PostResult>>> {
        val response = postService.editPost(index, EditPostRequest(content, uris.map { it.toString() }), multipartImgs)
        return getResourceFlow(response)
    }

    override suspend fun reportPost(
        postId: Long,
        reason: String,
        details: String
    ): Flow<Resource<ApiResponse<String>>> {
        val response = postService.reportPost(postId, ReportRequest(reason, details))
        return getResourceFlow(response)
    }
}
