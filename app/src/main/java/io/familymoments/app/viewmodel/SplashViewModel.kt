package io.familymoments.app.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.model.SplashUiState
import io.familymoments.app.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    private val _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState = _splashUiState.asStateFlow()
    fun checkUserValidation() {
        async(
            operation = { loginRepository.checkValidation() },
            onSuccess = {
                _splashUiState.value = _splashUiState.value.copy(
                    isLoading = isLoading.value,
                    isSuccess = true
                )
            },
            onFailure = {
                _splashUiState.value = _splashUiState.value.copy(
                    isLoading = isLoading.value,
                    isSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }
}
