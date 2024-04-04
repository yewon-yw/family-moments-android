package io.familymoments.app.feature.modifypassword.model.uistate

import androidx.annotation.StringRes

data class ModifyPasswordValidUiState(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val errorMessage: String? = null,
    val currentPasswordUiState: CurrentPasswordUiState = CurrentPasswordUiState(),
    val newPasswordUiState: NewPasswordUiState = NewPasswordUiState()
)

data class CurrentPasswordUiState(
    val valid: Boolean = false,
    @StringRes val warningResId: Int? = null,
    val reset: Boolean = false
)

data class NewPasswordUiState(
    val reset: Boolean = false,
    @StringRes val warningResId: Int? = null,
    val newPasswordValid: NewPasswordValid = NewPasswordValid(),
    val newPasswordCheckValid: NewPasswordValid = NewPasswordValid()
)

data class NewPasswordValid(
    val valid: Boolean = false,
    val hideWarningBorder: Boolean = true
)
