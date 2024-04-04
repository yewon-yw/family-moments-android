package io.familymoments.app.feature.signup.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.SignInRepository
import io.familymoments.app.feature.signup.model.UserInfoFormatChecker
import io.familymoments.app.feature.signup.model.mapper.toRequest
import io.familymoments.app.feature.signup.model.uistate.SignUpInfoUiState
import io.familymoments.app.feature.signup.model.uistate.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signInRepository: SignInRepository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun checkIdFormat(id: String) {
        _uiState.update {
            it.copy(
                signUpValidatedUiState = it.signUpValidatedUiState.copy(
                    userIdValidated = UserInfoFormatChecker.checkId(
                        id
                    )
                )
            )
        }
    }

    fun checkPasswordFormat(password: String) {
        _uiState.update {
            it.copy(
                signUpValidatedUiState = it.signUpValidatedUiState.copy(
                    passwordValidated = UserInfoFormatChecker.checkPassword(
                        password
                    )
                )
            )
        }
    }

    fun checkEmailFormat(email: String) {
        _uiState.update {
            it.copy(
                signUpValidatedUiState = it.signUpValidatedUiState.copy(
                    emailValidated = UserInfoFormatChecker.checkEmail(
                        email
                    )
                )
            )
        }
    }

    fun checkNicknameFormat(nickname: String) {
        _uiState.update {
            it.copy(
                signUpValidatedUiState = it.signUpValidatedUiState.copy(
                    nicknameValidated = UserInfoFormatChecker.checkNickname(
                        nickname
                    )
                )
            )
        }
    }

    fun checkBirthDayFormat(birthDay: String) {
        _uiState.update {
            it.copy(
                signUpValidatedUiState = it.signUpValidatedUiState.copy(
                    birthDayValidated = UserInfoFormatChecker.checkBirthDay(
                        birthDay
                    )
                )
            )
        }
    }

    fun checkIdDuplication(id: String) {
        async(
            operation = { signInRepository.checkId(id) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        signUpValidatedUiState = it.signUpValidatedUiState.copy(
                            userIdDuplicated = true
                        )
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        signUpValidatedUiState = it.signUpValidatedUiState.copy(
                            userIdDuplicated = false
                        )
                    )
                }
            })
    }

    fun checkEmailDuplication(email: String) {
        async(
            operation = { signInRepository.checkEmail(email) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        signUpValidatedUiState = it.signUpValidatedUiState.copy(
                            emailDuplicated = true
                        )
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        signUpValidatedUiState = it.signUpValidatedUiState.copy(
                            emailDuplicated = false
                        )
                    )
                }
            }
        )
    }

    fun executeSignUp(signUpInfoUiState: SignUpInfoUiState) {
        check(signUpInfoUiState.imgFile != null) { throw NullPointerException() }
        val imageRequestBody = signUpInfoUiState.imgFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart =
            MultipartBody.Part.createFormData("profileImg", signUpInfoUiState.imgFile.name, imageRequestBody)
        async(
            operation = { signInRepository.executeSignUp(profileImgPart, signUpInfoUiState.toRequest()) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        signUpSuccess = true
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        signUpSuccess = false
                    )
                }
            }
        )
    }
}
