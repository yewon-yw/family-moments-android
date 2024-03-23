package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResponse
import io.familymoments.app.feature.postdetail.model.response.PostCommentResponse
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun getCommentsByPostIndex(index:Int): Flow<Resource<GetCommentsByPostIndexResponse>>
    suspend fun postComment(comment:String, index: Int):Flow<Resource<PostCommentResponse>>
}
