package io.familymoments.app.feature.join.model.uistate

sealed class JoinUiState {
    data object Success : JoinUiState()
    data class Error(val message: String) : JoinUiState()
}
