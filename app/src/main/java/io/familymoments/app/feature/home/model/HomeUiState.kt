package io.familymoments.app.feature.home.model

data class HomeUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val posts: List<Post> = emptyList()
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST && posts.isEmpty()

    companion object {
        private const val NO_POST = "post가 존재하지 않습니다."
    }
}
