package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.PostService
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.calendar.model.GetPostsByMonthResponse
import io.familymoments.app.feature.home.model.GetPostsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val postService: PostService
) : PostRepository {
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

    override suspend fun loadMorePosts(familyId: Long, postId: Long): Flow<Resource<GetPostsResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = postService.loadMorePosts(familyId, postId)
            val responseBody = response.body() ?: GetPostsResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                // 새로운 Post가 없으면 404 : post가 존재하지 않습니다 에러 발생
                // 새로운 Post가 없으면 더 이상 로드하지 않으면 되니까, 굳이 flow를 emit하여 UiState를 업데이트할 필요가 없음
                if (responseBody.code != 404) {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun getPostsByMonth(
        familyId: Long,
        year: Int,
        month: Int
    ): Flow<Resource<GetPostsByMonthResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = postService.getPostsByMonth(familyId, year, month)
            val responseBody = response.body() ?: GetPostsByMonthResponse()

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
