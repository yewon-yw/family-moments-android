package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.CommentService
import io.familymoments.app.core.network.repository.CommentRepository
import io.familymoments.app.core.network.dto.request.CommentLovesRequest
import io.familymoments.app.core.network.dto.request.PostCommentRequest
import io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse
import io.familymoments.app.core.network.dto.response.DeleteCommentResponse
import io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse
import io.familymoments.app.core.network.dto.response.PostCommentLovesResponse
import io.familymoments.app.core.network.dto.response.PostCommentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentService: CommentService
) : CommentRepository {
    override suspend fun getPostComments(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = commentService.getPostComments(index)
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun postComment(comment: String, index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.PostCommentResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = commentService.postComment(index,
                io.familymoments.app.core.network.dto.request.PostCommentRequest(comment)
            )
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.PostCommentResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun deleteComment(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeleteCommentResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = commentService.deleteComment(index)
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.DeleteCommentResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun postCommentLoves(commentId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.PostCommentLovesResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = commentService.postCommentLoves(
                io.familymoments.app.core.network.dto.request.CommentLovesRequest(
                    commentId
                )
            )
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.PostCommentLovesResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun deleteCommentLoves(commentId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse>> {
        return flow {
            emit(Resource.Loading)
            Timber.tag("hkhk").d("삭제 수행")
            val response = commentService.deleteCommentLoves(
                io.familymoments.app.core.network.dto.request.CommentLovesRequest(
                    commentId
                )
            )
            val responseBody = response.body() ?: io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }
}
