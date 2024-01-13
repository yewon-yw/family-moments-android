package io.familymoments.app.viewmodel

import android.graphics.Bitmap
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.model.EmailValidator
import io.familymoments.app.model.JoinInfoUiModel
import io.familymoments.app.model.PasswordValidator
import io.familymoments.app.model.UserIdValidator
import io.familymoments.app.model.toRequest
import io.familymoments.app.repository.JoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(private val joinRepository: JoinRepository) :
        BaseViewModel() {

    private val _userIdValidation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userIdValidation: StateFlow<Boolean> = _userIdValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordValidation: StateFlow<Boolean> = _passwordValidation.asStateFlow()

    private val _emailValidation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val emailValidation: StateFlow<Boolean> = _emailValidation.asStateFlow()

    private val _userIdDuplicationCheck: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userIdDuplicationCheck: StateFlow<Boolean> = _userIdDuplicationCheck.asStateFlow()

    private val _emailDuplicationCheck: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val emailDuplicationCheck: StateFlow<Boolean> = _emailDuplicationCheck.asStateFlow()

    fun checkIdValidation(id: String) {
        _userIdValidation.value = UserIdValidator().isValid(id)
    }

    fun checkPasswordValidation(password: String) {
        _passwordValidation.value = PasswordValidator().isValid(password)
    }

    fun checkEmailValidation(email: String) {
        _emailValidation.value = EmailValidator().isValid(email)
    }

    fun checkIdDuplicate(id: String) {
        async(
                operation = { joinRepository.checkId(id) },
                onSuccess = { _userIdDuplicationCheck.value = true },
                onFailure = { _userIdDuplicationCheck.value = false })
    }

    fun checkEmailDuplicate(email: String) {
        async(
                operation = { joinRepository.checkEmail(email) },
                onSuccess = { _emailDuplicationCheck.value = true },
                onFailure = { _emailDuplicationCheck.value = false })
    }

    fun join(joinInfoUiModel: JoinInfoUiModel){
        val profileImage:MultipartBody.Part = bitmapToMultipartBodyPart(joinInfoUiModel.bitmap)
        async(
                operation = { joinRepository.join(profileImage, joinInfoUiModel.toRequest()) },
                onSuccess = { Log.d("HKHK", "회원가입 성공!") },
                onFailure = { Log.d("HKHK", "회원가입 실패! ${it.message}")})
    }

    @Throws(IOException::class)
    fun bitmapToFile(bitmap: Bitmap, file: File): File {
        file.createNewFile()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArray)
        fileOutputStream.flush()
        fileOutputStream.close()

        return file
    }

    private fun bitmapToMultipartBodyPart(bitmap: Bitmap): MultipartBody.Part {
        val file = File.createTempFile("temp", null) // 임시 파일 생성
        val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), bitmapToFile(bitmap, file))
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }
}