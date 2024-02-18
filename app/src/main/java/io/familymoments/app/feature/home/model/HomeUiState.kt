package io.familymoments.app.feature.home.model

data class HomeUiState(
    val isLoading: Boolean? = null,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val posts: List<Post> = emptyList()
)
