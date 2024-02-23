package io.familymoments.app.feature.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    // TODO: 추후 familyId 수정 예정
    private val familyId: Long = 2
    private var minPostId: Long = 0

    fun getPosts() {
        Timber.d("getPosts")
        async(
            operation = { postRepository.getPosts(familyId) },
            onSuccess = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    posts = it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    fun loadMorePosts() {
        Timber.d("loadMorePosts")
        async(
            operation = { postRepository.loadMorePosts(familyId, minPostId) },
            onSuccess = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    posts = _homeUiState.value.posts + it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }
}
