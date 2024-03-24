package io.familymoments.app.feature.postdetail.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.CommentRepository
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.postdetail.model.uistate.CommentsUiState
import io.familymoments.app.feature.postdetail.model.uistate.DeleteCommentUiState
import io.familymoments.app.feature.postdetail.model.uistate.DeletePostUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostCommentUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostDetailUiState
import io.familymoments.app.feature.postdetail.model.uistate.PostLovesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    private val _commentsUiState: MutableStateFlow<CommentsUiState> =
        MutableStateFlow(CommentsUiState())
    val commentsUiState: StateFlow<CommentsUiState> =
        _commentsUiState.asStateFlow()

    private val _postLovesUiState: MutableStateFlow<PostLovesUiState> =
        MutableStateFlow(PostLovesUiState())
    val postLovesUiState: StateFlow<PostLovesUiState> =
        _postLovesUiState.asStateFlow()

    private val _postCommentUiState: MutableStateFlow<PostCommentUiState> =
        MutableStateFlow(PostCommentUiState())
    val postCommentUiState: StateFlow<PostCommentUiState> =
        _postCommentUiState.asStateFlow()

    private val _deleteCommentUiState: MutableStateFlow<DeleteCommentUiState> =
        MutableStateFlow(DeleteCommentUiState())
    val deleteCommentUiState: StateFlow<DeleteCommentUiState> =
        _deleteCommentUiState.asStateFlow()

    private val _deletePostUiState: MutableStateFlow<DeletePostUiState> =
        MutableStateFlow(DeletePostUiState())
    val deletePostUiState: StateFlow<DeletePostUiState> =
        _deletePostUiState.asStateFlow()

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

    fun getCommentsByPostIndex(index: Int) {
        async(
            operation = { commentRepository.getCommentsByPostIndex(index) },
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

    fun getPostLovesByIndex(index: Int) {
        async(
            operation = { postRepository.getPostLovesByIndex(index) },
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

    fun postComment(index: Int, content: String) {
        async(
            operation = { commentRepository.postComment(content, index) },
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

    fun deleteComment(index: Int) {
        async(
            operation = { commentRepository.deleteComment(index) },
            onSuccess = {
                _deleteCommentUiState.value = _deleteCommentUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
            },
            onFailure = {
                _deleteCommentUiState.value = _deleteCommentUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    message = it.message
                )
            }
        )
    }

    fun postCommentLoves(commentId: Int) {
        async(
            operation = { commentRepository.postCommentLoves(commentId) },
            onSuccess = {
            },
            onFailure = {
            }
        )
    }

    fun deleteCommentLoves(commentId: Int) {
        async(
            operation = { commentRepository.deleteCommentLoves(commentId) },
            onSuccess = {
            },
            onFailure = {
            }
        )
    }

    fun postPostLoves(postId: Int) {
        async(
            operation = { postRepository.postPostLoves(postId) },
            onSuccess = {
            },
            onFailure = {
            }
        )
    }

    fun deletePostLoves(postId: Int) {
        async(
            operation = { postRepository.deletePostLoves(postId) },
            onSuccess = {},
            onFailure = {}
        )
    }

    fun deletePost(index: Int) {
        async(
            operation = { postRepository.deletePost(index) },
            onSuccess = {
                _deletePostUiState.value = _deletePostUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.message
                )
            },
            onFailure = {
                _deletePostUiState.value = _deletePostUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    result = it.message
                )
            }
        )
    }

    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    fun formatPostCreatedDate(createdAt: String): String {
        val date = LocalDate.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formattedString = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        return "$formattedString (${
            when (date.dayOfWeek) {
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
                DayOfWeek.SUNDAY -> "일"
            }
        })"
    }

    fun formatCommentCreatedDate(createdAt: String): String {
        val date = LocalDateTime.parse(createdAt)
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    }
}
