package io.familymoments.app.feature.postdetail.model.uistate

data class DeleteCommentUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: String? = null
)
