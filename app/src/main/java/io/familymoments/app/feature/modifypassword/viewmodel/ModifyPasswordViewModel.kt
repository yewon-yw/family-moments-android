package io.familymoments.app.feature.modifypassword.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.HttpResponse.INCORRECT_CURRENT_PASSWORD
import io.familymoments.app.core.network.HttpResponse.NEW_PASSWORD_SAME_AS_CURRENT
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.modifypassword.model.WarningType
import io.familymoments.app.feature.modifypassword.model.mapper.toRequest
import io.familymoments.app.feature.modifypassword.model.uistate.ModifyPasswordUiState
import io.familymoments.app.feature.modifypassword.model.uistate.ModifyPasswordValidUiState
import io.familymoments.app.feature.modifypassword.validateCurrentPassword
import io.familymoments.app.feature.modifypassword.validateNewPasswordEqual
import io.familymoments.app.feature.modifypassword.validateNewPasswordFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ModifyPasswordViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    private val _modifyPasswordValidUiState: MutableStateFlow<ModifyPasswordValidUiState> =
        MutableStateFlow(ModifyPasswordValidUiState())
    val modifyPasswordValidUiState: StateFlow<ModifyPasswordValidUiState> = _modifyPasswordValidUiState.asStateFlow()

    private val _modifyPasswordUiState: MutableStateFlow<ModifyPasswordUiState> =
        MutableStateFlow(ModifyPasswordUiState())
    val modifyPasswordUiState: StateFlow<ModifyPasswordUiState> = _modifyPasswordUiState.asStateFlow()

    fun checkCurrentPassword(password: String) {
        _modifyPasswordValidUiState.update {
            it.copy(
                currentPasswordUiState = it.currentPasswordUiState.copy(
                    valid = validateCurrentPassword(password),
                    warningResId = null
                )
            )
        }
    }

    fun checkPasswordFormat(newPassword: String) {
        val (valid, warningResId) = validateNewPasswordFormat(newPassword)
        _modifyPasswordValidUiState.update {
            it.copy(
                newPasswordUiState = it.newPasswordUiState.copy(
                    newPasswordValid = it.newPasswordUiState.newPasswordValid.copy(
                        valid = valid,
                        hideWarningBorder = valid || newPassword.isEmpty()
                    ),
                    warningResId = warningResId
                )
            )
        }
    }

    fun checkPasswordEqual(newPassword: String, newPasswordCheck: String) {
        val (valid, warningResId) = validateNewPasswordEqual(newPassword, newPasswordCheck)
        _modifyPasswordValidUiState.update {
            it.copy(
                newPasswordUiState = it.newPasswordUiState.copy(
                    newPasswordCheckValid = it.newPasswordUiState.newPasswordCheckValid.copy(
                        valid = valid,
                        hideWarningBorder = valid || newPasswordCheck.isEmpty()
                    ),
                    warningResId = it.newPasswordUiState.warningResId ?: warningResId
                )
            )
        }
    }

    fun requestModifyPassword(passwordUiState: ModifyPasswordUiState) {
        async(
            operation = { userRepository.modifyPassword(passwordUiState.toRequest()) },
            onSuccess = {
                val incorrectCurrentPassword = it.code == INCORRECT_CURRENT_PASSWORD
                val newPasswordSameAsCurrent = it.code == NEW_PASSWORD_SAME_AS_CURRENT
                _modifyPasswordValidUiState.update { uiState ->
                    uiState.copy(
                        isSuccess = it.isSuccess,
                        code = it.code,
                        currentPasswordUiState = uiState.currentPasswordUiState.copy(
                            warningResId = if (incorrectCurrentPassword) WarningType.IncorrectCurrentPassword.stringResId else null,
                            reset = incorrectCurrentPassword
                        ),
                        newPasswordUiState = uiState.newPasswordUiState.copy(
                            warningResId = if (newPasswordSameAsCurrent) WarningType.NewPasswordSameAsCurrent.stringResId else null,
                            reset = newPasswordSameAsCurrent,
                            newPasswordValid = uiState.newPasswordUiState.newPasswordValid.copy(
                                hideWarningBorder = !newPasswordSameAsCurrent
                            ),
                            newPasswordCheckValid = uiState.newPasswordUiState.newPasswordCheckValid.copy(
                                hideWarningBorder = !newPasswordSameAsCurrent
                            )
                        )
                    )
                }
            },
            onFailure = {
                _modifyPasswordValidUiState.value = _modifyPasswordValidUiState.value.copy(
                    isSuccess = false,
                    errorMessage = it.message,
                )
            }
        )
    }

    fun updateCurrentPassword(password: String) {
        _modifyPasswordUiState.value = _modifyPasswordUiState.value.copy(password = password)
    }

    fun updateNewPassword(newPassword: String, newPasswordCheck: String) {
        _modifyPasswordUiState.value =
            _modifyPasswordUiState.value.copy(newPassword = newPassword, newPasswordCheck = newPasswordCheck)
    }

    fun resetCurrentPasswordField() {
        _modifyPasswordValidUiState.update {
            it.copy(
                currentPasswordUiState = it.currentPasswordUiState.copy(reset = false)
            )
        }
    }

    fun resetNewPasswordField() {
        _modifyPasswordValidUiState.update {
            it.copy(
                newPasswordUiState = it.newPasswordUiState.copy(reset = false)
            )
        }
    }
}
