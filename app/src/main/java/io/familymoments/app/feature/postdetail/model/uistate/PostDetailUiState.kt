package io.familymoments.app.feature.postdetail.model.uistate

import io.familymoments.app.feature.postdetail.model.response.GetPostByIndexResult

data class PostDetailUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val message: String? = null,
    val result: GetPostByIndexResult = GetPostByIndexResult()
)
