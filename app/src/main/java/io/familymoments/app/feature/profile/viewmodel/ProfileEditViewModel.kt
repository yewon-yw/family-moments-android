package io.familymoments.app.feature.profile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.dto.request.ProfileEditRequest
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.util.EventManager
import io.familymoments.app.core.util.FileUtil
import io.familymoments.app.core.util.FileUtil.uriToFile
import io.familymoments.app.core.util.UserEvent
import io.familymoments.app.feature.profile.uistate.ProfileEditUiState
import io.familymoments.app.feature.signup.UserInfoFormatChecker.checkNickname
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val eventManager: EventManager,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val nickname: String = checkNotNull(savedStateHandle[Route.ProfileEdit.nicknameArg])
    private val profileImg: String = checkNotNull(savedStateHandle[Route.ProfileEdit.profileImgArg])
    private var index: Int = 0

    // 파일 index 업데이트
    private fun updateIndex() {
        index = 1 - index
    }

    private val _uiState: MutableStateFlow<ProfileEditUiState> =
        MutableStateFlow(ProfileEditUiState(nickname = nickname))
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    init {
        getFile()
    }

    private fun getFile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = uriToFile(Uri.parse(profileImg), 0)
                _uiState.value = _uiState.value.copy(profileImage = file)
            } catch (e: FileNotFoundException) {
                Timber.e("file not found error ${e.message}")
            }
        }
    }

    fun imageChanged(context: Context, uri: Uri) {
        try {
            updateIndex()
            val file = FileUtil.imageFileResize(context, uri, index)
            _uiState.value = _uiState.value.copy(profileImage = file)
        } catch (e: Exception) {
            Timber.e("image resize error ${e.message}")
        }
    }

    fun validateNickname(nickname: String) {
        _uiState.value = _uiState.value.copy(
            nicknameValidated = checkNickname(nickname)
        )
    }

    fun editUserProfile(imageFile: File, nickname: String) {
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart = MultipartBody.Part.createFormData("profileImg", imageFile.name, imageRequestBody)
        async(
            operation = {
                showLoading()
                userRepository.editUserProfile(
                    profileEditRequest = ProfileEditRequest(nickname),
                    profileImg = profileImgPart
                )
            },
            onSuccess = {
                viewModelScope.launch {
                    eventManager.sendEvent(UserEvent.ProfileChanged)
                }
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
