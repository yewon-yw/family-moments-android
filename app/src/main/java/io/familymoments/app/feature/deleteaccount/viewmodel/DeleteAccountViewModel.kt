package io.familymoments.app.feature.deleteaccount.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.deleteaccount.uistate.DeleteAccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<DeleteAccountUiState> = MutableStateFlow(DeleteAccountUiState())
    val uiState: StateFlow<DeleteAccountUiState> = _uiState.asStateFlow()

    fun deleteAccount() {
        async(
            operation = { userRepository.deleteAccount() },
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        result = response.result,
                        showPopup = true
                    )
                }
            },
            onFailure = { response ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = response.message,
                        showPopup = false
                    )
                }

            }
        )
    }

    fun resetSuccess() {
        _uiState.update {
            it.copy(
                isSuccess = null
            )
        }
    }
}
