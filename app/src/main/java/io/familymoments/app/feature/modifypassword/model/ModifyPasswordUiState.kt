package io.familymoments.app.feature.modifypassword.model

data class ModifyPasswordUiState(
    val currentPasswordValid: Boolean = false,
    val newPasswordValid: Boolean = false,
    val newPasswordWarning: Int? = null
)
