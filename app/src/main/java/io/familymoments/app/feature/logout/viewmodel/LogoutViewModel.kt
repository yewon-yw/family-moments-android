package io.familymoments.app.feature.logout.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.logout.model.uistate.LogoutUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _logoutUiState: MutableStateFlow<LogoutUiState> = MutableStateFlow(LogoutUiState())
    val logoutUiState: StateFlow<LogoutUiState> = _logoutUiState.asStateFlow()

    fun logout() {
        async(
            operation = { userRepository.logoutUser() },
            onSuccess = { _logoutUiState.value = _logoutUiState.value.copy(isSuccess = true) },
            onFailure = { _logoutUiState.value = _logoutUiState.value.copy(isSuccess = false, errorMessage = it.message) }
        )
    }
}
