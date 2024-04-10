package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse
import io.familymoments.app.core.network.dto.response.DeleteCommentResponse
import io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse
import io.familymoments.app.core.network.dto.response.PostCommentLovesResponse
import io.familymoments.app.core.network.dto.response.PostCommentResponse
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun getPostComments(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.GetCommentsIndexResponse>>
    suspend fun postComment(comment: String, index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.PostCommentResponse>>
    suspend fun deleteComment(index: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeleteCommentResponse>>
    suspend fun postCommentLoves(commentId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.PostCommentLovesResponse>>
    suspend fun deleteCommentLoves(commentId: Long): Flow<Resource<io.familymoments.app.core.network.dto.response.DeleteCommentLovesResponse>>
}
