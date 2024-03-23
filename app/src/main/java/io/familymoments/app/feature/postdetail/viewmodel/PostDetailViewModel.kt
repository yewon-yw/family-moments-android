package io.familymoments.app.feature.postdetail.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.CommentRepository
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.postdetail.model.uistate.CommentsUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostCommentUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostDetailUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostLovesUiState
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

    private val _commentsUiState:MutableStateFlow<CommentsUiState> =
        MutableStateFlow(CommentsUiState())
    val commentsUiState:StateFlow<CommentsUiState> =
        _commentsUiState.asStateFlow()

    private val _postLovesUiState:MutableStateFlow<PostLovesUiState> =
        MutableStateFlow(PostLovesUiState())
    val postLovesUiState:StateFlow<PostLovesUiState> =
        _postLovesUiState.asStateFlow()

    private val _postCommentUiState:MutableStateFlow<PostCommentUiState> =
        MutableStateFlow(PostCommentUiState())
    val postCommentUiState:StateFlow<PostCommentUiState> =
        _postCommentUiState.asStateFlow()

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

    fun getCommentsByPostIndex(index:Int){
        async(
            operation = {commentRepository.getCommentsByPostIndex(index)},
            onSuccess = {
                _commentsUiState.value = _commentsUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
            },
            onFailure = {
                _commentsUiState.value = _commentsUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    message = it.message
                )
            }
        )
    }

    fun getPostLovesByIndex(index:Int){
        async(
            operation = {postRepository.getPostLovesByIndex(index)},
            onSuccess = {
                _postLovesUiState.value = _postLovesUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.results
                )
            },
            onFailure = {
                _postLovesUiState.value = _postLovesUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    message = it.message
                )
            }
        )
    }

    fun postComment(index:Int, content:String){
        async(
            operation = {commentRepository.postComment(content, index)},
            onSuccess = {
                _postCommentUiState.value = _postCommentUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
            },
            onFailure = {
                _postCommentUiState.value = _postCommentUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    message = it.message
                )
            }
        )
    }
}
