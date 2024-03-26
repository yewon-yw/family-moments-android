package io.familymoments.app.core.uistate

data class PostItemUiState(
    val loves:Int = 0,
    val postPostLovesUiState: PostPostLovesUiState = PostPostLovesUiState(),
    val deletePostLovesUiState: DeletePostLovesUiState = DeletePostLovesUiState(),
    val deletePostUiState: DeletePostUiState = DeletePostUiState(),
    val logics: PostLogics = PostLogics()
)

data class PostLogics(
    val postPostLoves: (Long) -> Unit = {},
    val deletePostLoves: (Long) -> Unit = {},
    val deletePost: (Long) -> Unit = {},
    val reportPost: (String) -> Unit = {}
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
