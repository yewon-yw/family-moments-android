package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.feature.postdetail.model.response.DeleteCommentLovesResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentLovesResponse
import io.familymoments.app.feature.postdetail.model.response.DeleteCommentResponse
import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentResponse
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun getPostComments(index:Int): Flow<Resource<GetCommentsByPostIndexResponse>>
    suspend fun postComment(comment:String, index: Int):Flow<Resource<PostCommentResponse>>
    suspend fun deleteComment(index:Int):Flow<Resource<DeleteCommentResponse>>
    suspend fun postCommentLoves(commentId:Int):Flow<Resource<PostCommentLovesResponse>>
    suspend fun deleteCommentLoves(commentId:Int):Flow<Resource<DeleteCommentLovesResponse>>
}
