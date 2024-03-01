package io.familymoments.app.feature.join.model.uistate

data class JoinFormatValidatedUiState(
    val userIdFormatValidated: Boolean = false,
    val passwordFormatValidated: Boolean = false,
    val emailFormatValidated: Boolean = false,
    val nicknameFormatValidated: Boolean = false
)
