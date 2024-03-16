package io.familymoments.app.feature.signup.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.SignInRepository
import io.familymoments.app.core.util.convertBitmapToFile
import io.familymoments.app.feature.signup.model.SignUpInfoUiModel
import io.familymoments.app.feature.signup.model.UserInfoFormatChecker
import io.familymoments.app.feature.signup.model.mapper.toRequest
import io.familymoments.app.feature.signup.model.uistate.SignUpFormatValidatedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signInRepository: SignInRepository) :
    BaseViewModel() {

    private val _signUpFormatValidatedUiState: MutableStateFlow<SignUpFormatValidatedUiState> =
        MutableStateFlow(SignUpFormatValidatedUiState())
    var signUpFormatValidatedUiState: StateFlow<SignUpFormatValidatedUiState> =
        _signUpFormatValidatedUiState.asStateFlow()

    private val _userIdDuplicationCheck: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val userIdDuplicationCheck: StateFlow<Boolean?> = _userIdDuplicationCheck.asStateFlow()

    private val _emailDuplicationCheck: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val emailDuplicationCheck: StateFlow<Boolean?> = _emailDuplicationCheck.asStateFlow()

    fun checkIdFormat(id: String) {
        _signUpFormatValidatedUiState.value =
            _signUpFormatValidatedUiState.value.copy(userIdFormatValidated = UserInfoFormatChecker.checkId(id))
    }

    fun checkPasswordFormat(password: String) {
        _signUpFormatValidatedUiState.value = _signUpFormatValidatedUiState.value.copy(
            passwordFormatValidated = UserInfoFormatChecker.checkPassword(password)
        )
    }

    fun checkEmailFormat(email: String) {
        _signUpFormatValidatedUiState.value =
            _signUpFormatValidatedUiState.value.copy(emailFormatValidated = UserInfoFormatChecker.checkEmail(email))
    }

    fun checkNicknameFormat(nickname: String) {
        _signUpFormatValidatedUiState.value = _signUpFormatValidatedUiState.value.copy(
            nicknameFormatValidated = UserInfoFormatChecker.checkNickname(nickname)
        )
    }

    fun checkIdDuplication(id: String) {
        async(
            operation = { signInRepository.checkId(id) },
            onSuccess = { _userIdDuplicationCheck.value = true },
            onFailure = { _userIdDuplicationCheck.value = false })
    }

    fun checkEmailDuplication(email: String) {
        async(
            operation = { signInRepository.checkEmail(email) },
            onSuccess = { _emailDuplicationCheck.value = true },
            onFailure = { _emailDuplicationCheck.value = false })
    }

    fun executeSignUp(signUpInfoUiModel: SignUpInfoUiModel) {
        val imageFile = convertBitmapToFile(signUpInfoUiModel.bitmap)
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart = MultipartBody.Part.createFormData("profileImg", imageFile.name, imageRequestBody)
        async(
            operation = { signInRepository.executeSignUp(profileImgPart, signUpInfoUiModel.toRequest()) },
            onSuccess = { },
            onFailure = { })
    }
}
