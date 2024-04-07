package io.familymoments.app.feature.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.home.model.HomeUiState
import io.familymoments.app.feature.home.model.PostPopupType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private var minPostId: Long = 0

    fun getNicknameDday() {
        Timber.d("getNicknameDday")
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.getNicknameDday(familyId)
            },
            onSuccess = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    nickname = it.result.nickname,
                    dday = it.result.dday
                )
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

    fun getPosts() {
        Timber.d("getPosts")
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.getPosts(familyId)
            },
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
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.loadMorePosts(familyId, minPostId)
            },
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

    fun postPostLoves(index: Long) {
        Timber.d("postPostLoves")
        async(
            operation = { postRepository.postPostLoves(index) },
            onSuccess = { response ->
                Timber.d("postPostLoves onSuccess: $response")
                _homeUiState.update {
                    it.copy(
                        posts = it.posts.map { post ->
                            if (post.postId == index) {
                                post.copy(loved = true)
                            } else {
                                post
                            }
                        },
                    )
                }
            },
            onFailure = { t ->
                Timber.d("postPostLoves onFailure: ${t.message}")
                _homeUiState.update {
                    it.copy(popup = PostPopupType.POST_LOVES_FAILURE)
                }
            }
        )
    }

    fun deletePostLoves(index: Long) {
        Timber.d("deletePostLoves")
        async(
            operation = { postRepository.deletePostLoves(index) },
            onSuccess = { response ->
                Timber.d("deletePostLoves onSuccess: $response")
                _homeUiState.update {
                    it.copy(
                        posts = it.posts.map { post ->
                            if (post.postId == index) {
                                post.copy(loved = false)
                            } else {
                                post
                            }
                        },
                    )
                }
            },
            onFailure = { t ->
                Timber.d("deletePostLoves onFailure: ${t.message}")
                _homeUiState.update {
                    it.copy(popup = PostPopupType.DELETE_LOVES_FAILURE)
                }
            }
        )
    }
}
