package io.familymoments.app.feature.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun getPosts(familyId: Long) {
        async(
            operation = { postRepository.getPosts(familyId) },
            onSuccess = {
                _homeUiState.value = _homeUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    posts = it.result
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
}
