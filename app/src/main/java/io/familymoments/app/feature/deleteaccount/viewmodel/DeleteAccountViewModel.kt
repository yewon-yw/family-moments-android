package io.familymoments.app.feature.deleteaccount.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.LoginType
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.network.social.KakaoAuth.kakaoUnlink
import io.familymoments.app.core.network.social.NaverAuth.naverUnlink
import io.familymoments.app.feature.deleteaccount.uistate.DeleteAccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val userRepository: UserRepository, private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<DeleteAccountUiState> = MutableStateFlow(DeleteAccountUiState())
    val uiState: StateFlow<DeleteAccountUiState> = _uiState.asStateFlow()

    fun deleteAccount() {
        async(
            operation = { userRepository.deleteAccount() },
            onSuccess = {
                val type = userInfoPreferencesDataSource.loadLoginType()
                when (type) {
                    LoginType.KAKAO -> kakaoUnlink { error -> handleDeleteAccountResult(error) }
                    LoginType.NAVER -> naverUnlink { error -> handleDeleteAccountResult(error) }
                    LoginType.NORMAL -> handleDeleteAccountResult(error = null)
                }
                userInfoPreferencesDataSource.resetPreferencesData()
            },
            onFailure = { response ->
                _uiState.update {
                    it.copy(
                        isSuccess = false, errorMessage = response.message
                    )
                }

            })
    }

    private fun handleDeleteAccountResult(error: Throwable?) {
        if (error == null) {
            _uiState.update {
                it.copy(
                    isSuccess = true,
                    showPopup = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isSuccess = false,
                    errorMessage = error.message
                )
            }
        }
    }

    fun resetSuccess() {
        _uiState.update {
            it.copy(
                isSuccess = null
            )
        }
    }

    fun resetPopup() {
        _uiState.update {
            it.copy(
                showPopup = false
            )
        }
    }
}
