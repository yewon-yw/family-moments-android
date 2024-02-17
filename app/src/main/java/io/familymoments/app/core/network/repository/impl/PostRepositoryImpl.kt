package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.PostService
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.home.model.GetPostsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val postService: PostService
): PostRepository {
    override suspend fun getPosts(familyId: Long): Flow<Resource<GetPostsResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = postService.getPosts(familyId)
            val responseBody = response.body() ?: GetPostsResponse()

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
