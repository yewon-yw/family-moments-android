package io.familymoments.app.core.network.repository

import io.familymoments.app.core.network.Resource
import io.familymoments.app.feature.home.model.GetPostsResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(familyId: Long): Flow<Resource<GetPostsResponse>>
    suspend fun loadMorePosts(familyId: Long, postId: Long): Flow<Resource<GetPostsResponse>>
}