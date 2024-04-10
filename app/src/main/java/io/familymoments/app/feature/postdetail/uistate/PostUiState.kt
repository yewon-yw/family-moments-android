package io.familymoments.app.feature.postdetail.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.GetPostDetailResult
import io.familymoments.app.core.network.dto.response.GetPostLovesResult

@Immutable
data class PostUiState(
    val getPostDetailUiState: GetPostDetailUiState = GetPostDetailUiState(),
    val getPostLovesUiState: GetPostLovesUiState = GetPostLovesUiState(),
    val postPostLovesUiState: PostPostLovesUiState = PostPostLovesUiState(),
    val deletePostLovesUiState: DeletePostLovesUiState = DeletePostLovesUiState(),
    val deletePostUiState: DeletePostUiState = DeletePostUiState(),
    val logics: PostLogics = PostLogics()
)

@Immutable
data class PostLogics(
    val getPostDetail: (Long) -> Unit = {},
    val getPostLoves: (Long) -> Unit = {},
    val postPostLoves: (Long) -> Unit = {},
    val deletePostLoves: (Long) -> Unit = {},
    val deletePost: (Long) -> Unit = {}
)

@Immutable
data class GetPostDetailUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: io.familymoments.app.core.network.dto.response.GetPostDetailResult = io.familymoments.app.core.network.dto.response.GetPostDetailResult(),
)

@Immutable
data class GetPostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: List<io.familymoments.app.core.network.dto.response.GetPostLovesResult> = listOf(),
)

@Immutable
data class PostPostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)

@Immutable
data class DeletePostLovesUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)

@Immutable
data class DeletePostUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null,
)
