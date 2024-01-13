package io.familymoments.app.ui.screen

sealed class JoinUiState {
    data object Success : JoinUiState()
    data class Error(val message: String) : JoinUiState()
}