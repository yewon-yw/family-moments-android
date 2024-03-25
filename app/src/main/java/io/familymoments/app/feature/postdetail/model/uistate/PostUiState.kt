package io.familymoments.app.feature.postdetail.model.uistate

import io.familymoments.app.feature.postdetail.model.response.GetPostResult
import io.familymoments.app.feature.postdetail.model.response.GetPostLovesByIndexResult

data class PostUiState(
    val getPostUiState: GetPostUiState = GetPostUiState(),
    val getPostLovesUiState: GetPostLovesUiState = GetPostLovesUiState(),
    val postPostLovesUiState: PostPostLovesUiState = PostPostLovesUiState(),
    val deletePostLovesUiState: DeletePostLovesUiState = DeletePostLovesUiState(),
    val deletePostUiState: DeletePostUiState = DeletePostUiState(),
    val logics: PostLogics = PostLogics()
)

data class PostLogics(
    val getPost: (Long) -> Unit = {},
    val getPostLoves: (Long) -> Unit = {},
    val postPostLoves: (Long) -> Unit = {},
    val deletePostLoves: (Long) -> Unit = {},
    val deletePost: (Long) -> Unit = {}
)

data class GetPostUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: GetPostResult = GetPostResult(),
)

data class GetPostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: List<GetPostLovesByIndexResult> = listOf(),
)

data class PostPostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)

data class DeletePostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)

data class DeletePostUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)
