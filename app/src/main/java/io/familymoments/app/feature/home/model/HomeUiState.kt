package io.familymoments.app.feature.home.model

import io.familymoments.app.core.network.HttpResponseMessage.NO_POST_404

data class HomeUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val nickname: String = "",
    val dday: String = "",
    val posts: List<Post> = emptyList()
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST_404 && posts.isEmpty()
}
