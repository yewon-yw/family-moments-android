package io.familymoments.app.feature.modifypassword.model.uistate

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class ModifyPasswordUiState(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val errorMessage: String? = null,
    val currentPasswordUiState: CurrentPasswordUiState = CurrentPasswordUiState(),
    val newPasswordUiState: NewPasswordUiState = NewPasswordUiState(),
    val newPasswordCheckUiState: NewPasswordCheckUiState = NewPasswordCheckUiState()
)

@Immutable
data class CurrentPasswordUiState(
    val currentPassword: String = "",
    val isValidated: Boolean = false,
    @StringRes val warningResId: Int? = null,
    val isReset: Boolean = false
)

@Immutable
data class NewPasswordUiState(
    val newPassword: String = "",
    val isValidated: Boolean = false,
    val showWarningBorder: Boolean = false,
    @StringRes val warningResId: Int? = null,
    val isReset: Boolean = false
)

@Immutable
data class NewPasswordCheckUiState(
    val newPasswordCheck: String = "",
    val isValidated: Boolean = false,
    val showWarningBorder: Boolean = false,
    @StringRes val warningResId: Int? = null,
    val isReset: Boolean = false
)
