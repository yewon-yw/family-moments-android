package io.familymoments.app.feature.postdetail.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.CommentRepository
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.postdetail.model.uistate.PostDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) : BaseViewModel() {

    private val _postDetailUiState: MutableStateFlow<PostDetailUiState> =
        MutableStateFlow(PostDetailUiState())
    val postDetailUiState: StateFlow<PostDetailUiState> =
        _postDetailUiState.asStateFlow()

    fun getPostByIndex(index: Int) {
        async(
            operation = { postRepository.getPostByIndex(index) },
            onSuccess = {
                _postDetailUiState.value = _postDetailUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
            },
            onFailure = {
                _postDetailUiState.value = _postDetailUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    message = it.message
                )
            })

    }
}
