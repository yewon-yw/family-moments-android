package io.familymoments.app.feature.modifypassword.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.HttpResponse.INCORRECT_CURRENT_PASSWORD
import io.familymoments.app.core.network.HttpResponse.NEW_PASSWORD_SAME_AS_CURRENT
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.modifypassword.model.WarningType
import io.familymoments.app.feature.modifypassword.model.mapper.toRequest
import io.familymoments.app.feature.modifypassword.model.uistate.ModifyPasswordUiState
import io.familymoments.app.feature.modifypassword.validateCurrentPassword
import io.familymoments.app.feature.modifypassword.validateNewPasswordEqual
import io.familymoments.app.feature.modifypassword.validateNewPasswordFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ModifyPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<ModifyPasswordUiState> = MutableStateFlow(ModifyPasswordUiState())
    val uiState: StateFlow<ModifyPasswordUiState> = _uiState.asStateFlow()

    fun checkCurrentPassword(password: String) {
        _uiState.update {
            it.copy(
                currentPasswordUiState = it.currentPasswordUiState.copy(
                    isValidated = validateCurrentPassword(password),
                    warningResId = null
                )
            )
        }
    }

    fun checkPasswordFormat(newPassword: String) {
        val (isValidated, warningResId) = validateNewPasswordFormat(newPassword)
        _uiState.update {
            it.copy(
                newPasswordUiState = it.newPasswordUiState.copy(
                    isValidated = isValidated,
                    showWarningBorder = !isValidated && newPassword.isNotEmpty(),
                    warningResId = warningResId
                )
            )
        }
    }

    fun checkPasswordEqual(newPassword: String, newPasswordCheck: String) {
        val (isValidated, warningResId) = validateNewPasswordEqual(newPassword, newPasswordCheck)
        _uiState.update {
            it.copy(
                newPasswordCheckUiState = it.newPasswordCheckUiState.copy(
                    isValidated = isValidated,
                    showWarningBorder = !isValidated && newPasswordCheck.isNotEmpty(),
                    warningResId = warningResId
                )
            )
        }
    }

    fun requestModifyPassword() {
        async(
            operation = { userRepository.modifyPassword(_uiState.value.toRequest()) },
            onSuccess = { response ->
                val incorrectCurrentPassword = response.code == INCORRECT_CURRENT_PASSWORD
                val newPasswordSameAsCurrent = response.code == NEW_PASSWORD_SAME_AS_CURRENT

                _uiState.update {
                    it.copy(
                        isSuccess = response.isSuccess,
                        code = response.code,
                        currentPasswordUiState = it.currentPasswordUiState.copy(
                            warningResId = if (incorrectCurrentPassword) WarningType.IncorrectCurrentPassword.stringResId else null,
                            isReset = incorrectCurrentPassword
                        ),
                        newPasswordUiState = it.newPasswordUiState.copy(
                            warningResId = if (newPasswordSameAsCurrent) WarningType.NewPasswordSameAsCurrent.stringResId else null,
                            isReset = newPasswordSameAsCurrent,
                            showWarningBorder = newPasswordSameAsCurrent,
                        ),
                        newPasswordCheckUiState = it.newPasswordCheckUiState.copy(
                            warningResId = if (newPasswordSameAsCurrent) WarningType.NewPasswordSameAsCurrent.stringResId else null,
                            isReset = newPasswordSameAsCurrent,
                            showWarningBorder = newPasswordSameAsCurrent
                        )
                    )
                }
            },
            onFailure = { t ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = t.message
                    )
                }
            }
        )
    }

    fun updatePasswordUiState(currentPassword: String, newPassword: String, newPasswordCheck: String) {
        _uiState.update {
            it.copy(
                currentPasswordUiState = it.currentPasswordUiState.copy(currentPassword = currentPassword),
                newPasswordUiState = it.newPasswordUiState.copy(newPassword = newPassword),
                newPasswordCheckUiState = it.newPasswordCheckUiState.copy(newPasswordCheck = newPasswordCheck)
            )
        }
    }

    fun onClearCurrentPassword() {
        // 현재 비밀번호 입력필드 clear 할 때 isReset = false로 초기화
        _uiState.update {
            it.copy(
                currentPasswordUiState = it.currentPasswordUiState.copy(isReset = false)
            )
        }
    }

    fun onClearNewPasswords() {
        // 새 비밀번호 입력필드, 새 비밀번호 확인 입력필드 clear 할 때 isReset = false로 초기화
        _uiState.update {
            it.copy(
                newPasswordUiState = it.newPasswordUiState.copy(isReset = false),
                newPasswordCheckUiState = it.newPasswordCheckUiState.copy(isReset = false)
            )
        }
    }
}
