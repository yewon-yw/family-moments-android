package io.familymoments.app.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.model.PasswordValidator
import io.familymoments.app.model.UserIdValidator
import io.familymoments.app.repository.JoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(private val joinRepository: JoinRepository) :
        BaseViewModel() {

    private val _userIdValidation: MutableStateFlow<Boolean> = MutableStateFlow(false)
     val userIdValidation: StateFlow<Boolean> = _userIdValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<Boolean> = MutableStateFlow(false)
     val passwordValidation: StateFlow<Boolean> = _passwordValidation.asStateFlow()

    private val _userIdDuplicationCheck: MutableStateFlow<Boolean> = MutableStateFlow(false)
     val userIdDuplicationCheck: StateFlow<Boolean> = _userIdDuplicationCheck.asStateFlow()

    private val _emailDuplicationCheck: MutableStateFlow<Boolean> = MutableStateFlow(false)
     val emailDuplicationCheck: StateFlow<Boolean> = _emailDuplicationCheck.asStateFlow()

    fun checkIdValidation(id: String) {
        _userIdValidation.value = UserIdValidator().isValid(id)
    }

    fun checkPasswordValidation(password: String) {
        _passwordValidation.value = PasswordValidator().isValid(password)
    }

    fun checkIdDuplicate(id: String) {
        async(
                operation = { joinRepository.checkId(id) },
                onSuccess = { _userIdDuplicationCheck.value = true },
                onFailure = { it.printStackTrace() })
    }

    fun checkEmailDuplicate(id: String) {
        async(
                operation = { joinRepository.checkId(id) },
                onSuccess = { _emailDuplicationCheck.value = true },
                onFailure = { it.printStackTrace() })
    }
}