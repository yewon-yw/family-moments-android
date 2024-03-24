package io.familymoments.app.feature.postdetail.model.uistate

import io.familymoments.app.feature.postdetail.model.response.GetCommentsByPostIndexResult

data class CommentUiState(
    val getCommentsUiState: GetCommentsUiState = GetCommentsUiState(),
    val deleteCommentUiState: DeleteCommentUiState = DeleteCommentUiState(),
    val postCommentUiState: PostCommentUiState = PostCommentUiState(),
    val postCommentLovesUiState: PostCommentLovesUiState = PostCommentLovesUiState(),
    val deleteCommentLovesUiState: DeleteCommentLovesUiState = DeleteCommentLovesUiState(),
    val logics: CommentLogics = CommentLogics()
)

data class CommentLogics(
    val getComments: (Int) -> Unit = {},
    val postComment: (Int, String) -> Unit = {_,_->},
    val deleteComment: (Int) -> Unit = {},
    val postCommentLoves: (Int) -> Unit = {},
    val deleteCommentLoves: (Int) -> Unit = {}
)

data class GetCommentsUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: List<GetCommentsByPostIndexResult> = listOf()

)

data class DeleteCommentUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

data class PostCommentUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

data class PostCommentLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)

data class DeleteCommentLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)
