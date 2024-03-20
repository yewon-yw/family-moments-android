package io.familymoments.app.feature.addpost.viewmodel

import android.graphics.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.addpost.model.AddPostUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AddPostUiState())
    val uiState = _uiState.asStateFlow()

    fun addPost(content: String, bitmapList: List<Bitmap?>) {
        val imageFiles = bitmapList.map { bitmap ->
            bitmapToFile(bitmap!!)
        }
        async(
            operation = { postRepository.addPost(2, content, imageFiles) },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        val file = File.createTempFile("temp", ".jpg") // 임시 파일 생성
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }
}
