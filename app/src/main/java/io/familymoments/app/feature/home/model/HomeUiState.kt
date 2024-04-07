package io.familymoments.app.feature.home.model

import io.familymoments.app.core.network.HttpResponseMessage.NO_POST_404

data class HomeUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val nickname: String = "",
    val dday: String = "",
    val posts: List<Post> = emptyList(),
    val popup: PostPopupType? = null
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST_404 && posts.isEmpty()
}

enum class PostPopupType {
    POST_LOVES_FAILURE,
    DELETE_LOVES_FAILURE,
    DELETE_POST,
    DELETE_POST_SUCCESS,
    DELETE_POST_FAILURE,
    REPORT_POST,
    REPORT_POST_SUCCESS,
    REPORT_POST_FAILURE
}
