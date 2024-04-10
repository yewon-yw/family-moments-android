package io.familymoments.app.core.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.core.uistate.CompletePopupUiState
import io.familymoments.app.core.uistate.DeletePopupUiState
import io.familymoments.app.core.uistate.PopupStatusLogics
import io.familymoments.app.core.uistate.PopupUiState
import io.familymoments.app.core.uistate.PostItemUiState
import io.familymoments.app.core.uistate.PostLogics
import io.familymoments.app.core.uistate.ReportPopupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostItemViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseViewModel() {
    private val _postItemUiState: MutableStateFlow<PostItemUiState> =
        MutableStateFlow(
            PostItemUiState(
                logics = PostLogics(
                    postPostLoves = this::postPostLoves,
                    deletePostLoves = this::deletePostLoves,
                    deletePost = this::deletePost
                )
            )
        )
    val postItemUiState: StateFlow<PostItemUiState> = _postItemUiState.asStateFlow()

    private val _popupUiState: MutableStateFlow<PopupUiState> = MutableStateFlow(
        PopupUiState(
            popupStatusLogics = PopupStatusLogics(
                this::showCompletePopup,
                this::showDeletePopup,
                this::showReportPopup
            )
        )
    )
    val popupUiState: StateFlow<PopupUiState> = _popupUiState.asStateFlow()

    fun postPostLoves(index: Long) {
        async(
            operation = { postRepository.postPostLoves(index) },
            onSuccess = {
                val postPostLovesUiState = _postItemUiState.value.postPostLovesUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    postPostLovesUiState = postPostLovesUiState.copy(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        result = it.result
                    )
                )
            },
            onFailure = {
                val postPostLovesUiState = _postItemUiState.value.postPostLovesUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    postPostLovesUiState = postPostLovesUiState.copy(
                        isSuccess = false,
                        isLoading = isLoading.value,
                        message = it.message
                    )
                )
            }
        )
    }

    fun deletePostLoves(index: Long) {
        async(
            operation = { postRepository.deletePostLoves(index) },
            onSuccess = {
                val deletePostLovesUiState = _postItemUiState.value.deletePostLovesUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    deletePostLovesUiState = deletePostLovesUiState.copy(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        result = it.result
                    )
                )
            },
            onFailure = {
                val deletePostLovesUiState = _postItemUiState.value.deletePostLovesUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    deletePostLovesUiState = deletePostLovesUiState.copy(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        message = it.message
                    )
                )
            }
        )
    }

    fun deletePost(index: Long) {
        async(
            operation = { postRepository.deletePost(index) },
            onSuccess = {
                val deletePostUiState = _postItemUiState.value.deletePostUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    deletePostUiState = deletePostUiState.copy(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        result = it.message
                    )
                )
            },
            onFailure = {
                val deletePostUiState = _postItemUiState.value.deletePostUiState
                _postItemUiState.value = _postItemUiState.value.copy(
                    deletePostUiState = deletePostUiState.copy(
                        isSuccess = false,
                        isLoading = isLoading.value,
                        result = it.message
                    )
                )
            }
        )
    }

    fun showCompletePopup(status: Boolean) {
        _popupUiState.value = _popupUiState.value.copy(
            completePopupUiState = CompletePopupUiState(show = status)
        )
    }

    fun showDeletePopup(status: Boolean, content: String, execute: () -> Unit) {
        _popupUiState.value = _popupUiState.value.copy(
            deletePopupUiState = DeletePopupUiState(status, content, execute)
        )
    }

    fun showReportPopup(status: Boolean, execute: () -> Unit) {
        _popupUiState.value = _popupUiState.value.copy(
            reportPopupUiState = ReportPopupUiState(status, execute)
        )
    }
}
