package io.familymoments.app.feature.modifypassword.model.uistate

data class ModifyPasswordValidUiState(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val errorMessage: String? = null,
    val currentPassword: ModifyPasswordValid = ModifyPasswordValid(),
    val newPassword: ModifyPasswordValid = ModifyPasswordValid()
)

data class ModifyPasswordValid (
    val valid: Boolean = false,
    val warning: Int? = null,
    val reset: Boolean = false
)
