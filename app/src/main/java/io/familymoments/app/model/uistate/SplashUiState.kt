package io.familymoments.app.model.uistate

data class SplashUiState(
    val isLoading: Boolean? = null,
    val isSuccess: Boolean? = null,
    val error: Throwable? = null
)
