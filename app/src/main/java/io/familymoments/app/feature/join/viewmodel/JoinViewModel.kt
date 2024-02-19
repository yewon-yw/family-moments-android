package io.familymoments.app.feature.join.viewmodel

import android.graphics.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PublicRepository
import io.familymoments.app.feature.join.model.UserInfoFormatChecker
import io.familymoments.app.feature.join.model.mapper.toRequest
import io.familymoments.app.feature.join.model.JoinInfoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(private val publicRepository: PublicRepository) :
        BaseViewModel() {

    private val _userIdFormatValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userIdFormatValidated: StateFlow<Boolean> = _userIdFormatValidated.asStateFlow()

    private val _passwordFormatValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordFormatValidated: StateFlow<Boolean> = _passwordFormatValidated.asStateFlow()

    private val _emailFormatValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val emailFormatValidated: StateFlow<Boolean> = _emailFormatValidated.asStateFlow()

    private val _nicknameFormatValidated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val nicknameFormatValidated: StateFlow<Boolean> = _nicknameFormatValidated.asStateFlow()

    private val _userIdDuplicationCheck: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val userIdDuplicationCheck: StateFlow<Boolean?> = _userIdDuplicationCheck.asStateFlow()

    private val _emailDuplicationCheck: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val emailDuplicationCheck: StateFlow<Boolean?> = _emailDuplicationCheck.asStateFlow()

    fun checkIdFormat(id: String) {
        _userIdFormatValidated.value = UserInfoFormatChecker.checkId(id)
    }

    fun checkPasswordFormat(password: String) {
        _passwordFormatValidated.value = UserInfoFormatChecker.checkPassword(password)
    }

    fun checkEmailFormat(email: String) {
        _emailFormatValidated.value = UserInfoFormatChecker.checkEmail(email)
    }

    fun checkNicknameFormat(nickname: String) {
        _nicknameFormatValidated.value = UserInfoFormatChecker.checkNickname(nickname)
    }

    fun checkIdDuplication(id: String) {
        async(
            operation = { publicRepository.checkId(id) },
            onSuccess = { _userIdDuplicationCheck.value = true },
            onFailure = { _userIdDuplicationCheck.value = false })
    }

    fun checkEmailDuplication(email: String) {
        async(
            operation = { publicRepository.checkEmail(email) },
            onSuccess = { _emailDuplicationCheck.value = true },
            onFailure = { _emailDuplicationCheck.value = false })
    }

    fun join(joinInfoUiModel: JoinInfoUiModel) {
        val imageFile = bitmapToFile(joinInfoUiModel.bitmap)
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart = MultipartBody.Part.createFormData("profileImg", imageFile.name, imageRequestBody)
        async(
                operation = { publicRepository.join(profileImgPart, joinInfoUiModel.toRequest()) },
                onSuccess = { },
                onFailure = { })
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        val file = File.createTempFile("profile_image", ".jpg") // 임시 파일 생성
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }
}
