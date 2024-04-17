package io.familymoments.app.feature.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.profile.mapper.toRequest
import io.familymoments.app.feature.profile.uistate.ProfileEditInfoUiState
import io.familymoments.app.feature.profile.uistate.ProfileEditUiState
import io.familymoments.app.feature.signup.UserInfoFormatChecker.checkBirthDay
import io.familymoments.app.feature.signup.UserInfoFormatChecker.checkNickname
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val name: String = checkNotNull(savedStateHandle[Route.ProfileEdit.nameArg])
    private val nickname: String = checkNotNull(savedStateHandle[Route.ProfileEdit.nicknameArg])
    private val birthdate: String = checkNotNull(savedStateHandle[Route.ProfileEdit.birthdateArg])
    private val profileImg: String = checkNotNull(savedStateHandle[Route.ProfileEdit.profileImgArg])

    private val _uiState: MutableStateFlow<ProfileEditUiState> = MutableStateFlow(
        ProfileEditUiState(
            profileEditInfoUiState = ProfileEditInfoUiState(name, nickname, birthdate),
            profileImage = Uri.parse(profileImg)
        )
    )
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    fun imageChanged(uri: Uri) {
        _uiState.value = _uiState.value.copy(
            profileImage = uri
        )
    }

    fun nameChanged(name: String) {
        _uiState.value = _uiState.value.copy(
            profileEditInfoUiState = _uiState.value.profileEditInfoUiState.copy(name = name),
            profileEditValidated = _uiState.value.profileEditValidated.copy(nameValidated = name.isNotEmpty())
        )
    }

    fun nicknameChanged(nickname: String) {
        _uiState.value = _uiState.value.copy(
            profileEditInfoUiState = _uiState.value.profileEditInfoUiState.copy(nickname = nickname),
            profileEditValidated = _uiState.value.profileEditValidated.copy(nicknameValidated = checkNickname(nickname))
        )
    }

    fun birthdateChanged(birthdate: String) {
        _uiState.value = _uiState.value.copy(
            profileEditInfoUiState = _uiState.value.profileEditInfoUiState.copy(birthdate = birthdate),
            profileEditValidated = _uiState.value.profileEditValidated.copy(birthdateValidated = checkBirthDay(birthdate))
        )
    }

    fun editUserProfile(imageFile: File) {
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart = MultipartBody.Part.createFormData("profileImg", imageFile.name, imageRequestBody)
        async(
            operation = {
                userRepository.editUserProfile(
                    profileEditRequest = _uiState.value.profileEditInfoUiState.toRequest(),
                    profileImg = profileImgPart
                )
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(isSuccess = true)
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }
}
