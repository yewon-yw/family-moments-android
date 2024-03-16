package io.familymoments.app.feature.logout.model.uistate

data class LogoutUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
