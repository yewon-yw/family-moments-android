package io.familymoments.app.feature.modifypassword.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.feature.modifypassword.model.WarningType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ModifyPasswordViewModel @Inject constructor() : BaseViewModel() {
    private val _currentPasswordValid = MutableStateFlow(true)
    val currentPasswordValid: StateFlow<Boolean> = _currentPasswordValid.asStateFlow()

    private val _newPasswordValid = MutableStateFlow(false)
    val newPasswordValid: StateFlow<Boolean> = _newPasswordValid.asStateFlow()

    private val _currentPasswordWarning = MutableStateFlow<WarningType?>(null)
    val currentPasswordWarning: StateFlow<WarningType?> = _currentPasswordWarning.asStateFlow()

    private val _newPasswordWarning = MutableStateFlow<WarningType?>(null)
    val newPasswordWarning: StateFlow<WarningType?> = _newPasswordWarning.asStateFlow()

    fun checkCurrentPassword(password: String) {
        _currentPasswordValid.value = password.isNotEmpty()
    }

    fun checkNewPassword(newPassword: String, newPasswordCheck: String) {
        if (newPassword.isEmpty() && newPasswordCheck.isEmpty()) {
            _newPasswordWarning.value = null
        } else if (!checkPasswordFormat(newPassword)) {
            _newPasswordWarning.value = WarningType.InvalidPasswordFormat
        } else if(newPasswordCheck.isEmpty()) {
            _newPasswordWarning.value = null
        } else if (newPassword != newPasswordCheck) {
            _newPasswordWarning.value = WarningType.NewPasswordsMismatch
        } else {
            _newPasswordValid.value = true
            _newPasswordWarning.value = null
        }
    }

    fun requestModifyPassword(current: String, new: String, newCheck: String) {

    }

    private fun checkPasswordFormat(password: String): Boolean {
        return password.matches(passwordRegex)
    }

    companion object {
        private val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{8,12}\$")
    }
}
