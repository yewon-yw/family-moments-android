package io.familymoments.app.feature.signup.model.uistate

data class SignUpFormatValidatedUiState(
    val userIdFormatValidated: Boolean = false,
    val passwordFormatValidated: Boolean = false,
    val emailFormatValidated: Boolean = false,
    val nicknameFormatValidated: Boolean = false
)
