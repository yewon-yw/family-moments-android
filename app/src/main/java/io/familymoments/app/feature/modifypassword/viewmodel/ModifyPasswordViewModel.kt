package io.familymoments.app.feature.modifypassword.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.feature.modifypassword.model.ModifyPasswordUiState
import io.familymoments.app.feature.modifypassword.validateCurrentPassword
import io.familymoments.app.feature.modifypassword.validateNewPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ModifyPasswordViewModel @Inject constructor() : BaseViewModel() {
    private val _modifyPasswordUiState: MutableStateFlow<ModifyPasswordUiState> = MutableStateFlow(ModifyPasswordUiState())
    val modifyPasswordUiState: StateFlow<ModifyPasswordUiState> = _modifyPasswordUiState.asStateFlow()

    private val _newPasswordWarning = MutableStateFlow<Int?>(null)
    val newPasswordWarning: StateFlow<Int?> = _newPasswordWarning.asStateFlow()

    fun checkCurrentPassword(password: String) {
        _modifyPasswordUiState.value = _modifyPasswordUiState.value.copy(
            currentPasswordValid = validateCurrentPassword(password)
        )
    }

    fun checkNewPassword(newPassword: String, newPasswordCheck: String) {
        val (newPasswordValid, newPasswordWarning) = validateNewPassword(newPassword, newPasswordCheck)
        _modifyPasswordUiState.value = _modifyPasswordUiState.value.copy(newPasswordValid = newPasswordValid)
        _newPasswordWarning.value = newPasswordWarning
    }

    fun requestModifyPassword(current: String, new: String, newCheck: String) {

    }
}
