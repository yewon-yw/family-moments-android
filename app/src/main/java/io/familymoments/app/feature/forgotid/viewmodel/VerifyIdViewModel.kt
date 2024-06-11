package io.familymoments.app.feature.forgotid.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.forgotid.uistate.VerifyIdUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VerifyIdViewModel @Inject constructor(
    val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(VerifyIdUiState())
    val uiState = _uiState.asStateFlow()

    fun sendEmail(name: String, email: String) {
        _uiState.update {
            it.copy(
                sendEmailUiState = it.sendEmailUiState.copy(
                    isLoading = true,
                )
            )
        }
        async(
            operation = {
                userRepository.sendEmail(name, email)
            },
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        sendEmailUiState = it.sendEmailUiState.copy(
                            isSuccess = true,
                            isLoading = false,
                            result = response.result
                        )
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        sendEmailUiState = it.sendEmailUiState.copy(
                            isSuccess = false,
                            isLoading = false,
                            message = throwable.message ?: ""
                        )
                    )
                }
            }
        )
    }

    fun findId(name: String, email: String, code: String) {
        async(
            operation = {
                userRepository.findId(name, email, code)
            },
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        findIdUiState = it.findIdUiState.copy(
                            isSuccess = true,
                            userId = response.result.userId
                        )
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        findIdUiState = it.findIdUiState.copy(
                            isSuccess = false,
                            message = throwable.message ?: ""
                        )
                    )
                }
            }
        )
    }

    fun resetSendEmailSuccess() {
        _uiState.update {
            it.copy(
                sendEmailUiState = it.sendEmailUiState.copy(
                    isSuccess = null,
                )
            )
        }
    }

    fun resetFindIdSuccess() {
        _uiState.update {
            it.copy(
                findIdUiState = it.findIdUiState.copy(
                    isSuccess = null,
                )
            )
        }
    }
}
