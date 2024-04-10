package io.familymoments.app.feature.login.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.login.uistate.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun loginUser(username: String, password: String) {
        async(
            operation = { userRepository.loginUser(username.trimEnd(), password.trimEnd()) },
            onSuccess = {
                _loginUiState.value = _loginUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    loginResult = it.result
                )
            },
            onFailure = {
                _loginUiState.value = _loginUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    // errorMessage가 더이상 나타나지 않도록 하기 위함
    fun updateSuccessNull() {
        if (_loginUiState.value.isSuccess != null) {
            _loginUiState.value = _loginUiState.value.copy(isSuccess = null)
        }
    }
}
