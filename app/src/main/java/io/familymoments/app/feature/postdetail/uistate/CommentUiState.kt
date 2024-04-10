package io.familymoments.app.feature.postdetail.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.GetCommentsResult

@Immutable
data class CommentUiState(
    val getCommentsUiState: GetCommentsUiState = GetCommentsUiState(),
    val deleteCommentUiState: DeleteCommentUiState = DeleteCommentUiState(),
    val postCommentUiState: PostCommentUiState = PostCommentUiState(),
    val postCommentLovesUiState: PostCommentLovesUiState = PostCommentLovesUiState(),
    val deleteCommentLovesUiState: DeleteCommentLovesUiState = DeleteCommentLovesUiState(),
    val logics: CommentLogics = CommentLogics()
)

@Immutable
data class CommentLogics(
    val getComments: (Long) -> Unit = {},
    val postComment: (Long, String) -> Unit = { _, _ -> },
    val deleteComment: (Long) -> Unit = {},
    val postCommentLoves: (Long) -> Unit = {},
    val deleteCommentLoves: (Long) -> Unit = {}
)

@Immutable
data class GetCommentsUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: List<io.familymoments.app.core.network.dto.response.GetCommentsResult> = listOf()
)

@Immutable
data class DeleteCommentUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

@Immutable
data class PostCommentUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

@Immutable
data class PostCommentLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

@Immutable
data class DeleteCommentLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)
