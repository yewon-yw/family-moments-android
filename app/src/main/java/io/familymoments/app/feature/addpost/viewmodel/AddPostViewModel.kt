package io.familymoments.app.feature.addpost.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.core.network.util.createImageMultiPart
import io.familymoments.app.core.util.FileUtil
import io.familymoments.app.core.util.POST_PHOTO_MAX_SIZE
import io.familymoments.app.feature.addpost.AddPostMode
import io.familymoments.app.feature.addpost.uistate.AddPostUiState
import io.familymoments.app.feature.addpost.uistate.ExistPostUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val resizedImageFiles: MutableList<File> = mutableStateListOf()
    private val mode: Int = savedStateHandle[Route.EditPost.modeArg] ?: AddPostMode.ADD.mode
    private val editPostId: Long = savedStateHandle[Route.EditPost.editPostIdArg] ?: 0
    private val editImages: Array<String> = savedStateHandle[Route.EditPost.editImagesArg] ?: arrayOf()

    // 기존 이미지 uri 변경 사항 저장
    private val existImagesUri: MutableList<Uri> = mutableListOf()
    private val editContent: String = savedStateHandle[Route.EditPost.editContentArg] ?: ""

    private val _uiState = MutableStateFlow(AddPostUiState())
    val uiState = _uiState.asStateFlow()

    val uriState = mutableStateListOf<Uri>()

    init {
        initUiState()
        // 기존 이미지 url 초기화
        getUriList()
    }

    private fun initUiState() {
        _uiState.update {
            it.copy(
                mode = when (mode) {
                    AddPostMode.ADD.mode -> AddPostMode.ADD
                    else -> AddPostMode.EDIT
                },
                existPostUiState = ExistPostUiState(
                    editPostId = editPostId,
                    editImages = getEditImagesUrlList(this.editImages),
                    editContent = editContent
                )
            )
        }
    }

    private fun getUriList() {
        _uiState.value.existPostUiState.editImages.forEach {
            val uri = Uri.parse(it)
            uriState.add(uri)
            existImagesUri.add(uri)
        }
    }

    private fun getEditImagesUrlList(editImages: Array<String>): List<String> {
        val regex = Regex("[\\[\\] ]")  // 문자열에서 공백, 대괄호 제거
        return editImages.getOrNull(0)?.replace(regex, "")?.replace("thumbnails", "fm-origin")?.split(",") ?: listOf()
    }

    suspend fun addPost(content: String) {
        showLoading()
        while (uriState.size != resizedImageFiles.size) {
            Timber.tag("Image").i("Image resizing...")
            delay(100)
        }

        viewModelScope.launch(Dispatchers.IO) {
            for (file in resizedImageFiles) {
                // 이미지 크기 Log 출력
                val size = file.length() / 1024
                Timber.tag("Image").i("Image Size: $size KB")
            }
        }

        val imagesMultipart = resizedImageFiles.map { file ->
            Timber.tag("Image").i("Image File: ${file.path}")
            createImageMultiPart(file, "imgs")
        }
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.addPost(familyId, content, imagesMultipart)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = true
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun editPost(index: Long, content: String) {
        showLoading()
        async(
            operation = {
                while (uriState.size - existImagesUri.size != resizedImageFiles.size) {
                    Timber.tag("Image").i("Image resizing...")
                    delay(200)
                }
                val imagesMultipart = resizedImageFiles.map { file ->
                    createImageMultiPart(file, "newImgs")
                }
                postRepository.editPost(index, content, existImagesUri, imagesMultipart)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = true,
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun addImages(uris: List<Uri>, context: Context, errorMessage: String) {
        showLoading()
        if (uris.size + uriState.size <= POST_PHOTO_MAX_SIZE) {
            uriState.addAll(uris)

            hideLoading()

            viewModelScope.launch(Dispatchers.IO) {
                uris.forEachIndexed { index, it ->
                    val file: File = FileUtil.imageFileResize(context, it, index)
                    resizedImageFiles.add(file)
                }
            }

        } else {
            val availableCount = POST_PHOTO_MAX_SIZE - uriState.size
            viewModelScope.launch(Dispatchers.Main) {
                uris.subList(0, availableCount).forEach {
                    uriState.add(it)
                    _uiState.update {
                        it.copy(
                            isSuccess = false,
                            errorMessage = errorMessage
                        )
                    }
                }
            }
            hideLoading()
            viewModelScope.launch(Dispatchers.IO) {
                uris.subList(0, availableCount).forEachIndexed { index, it ->
                    val file = FileUtil.imageFileResize(context, it, uriState.size + index)
                    resizedImageFiles.add(file)
                }
            }
        }
    }

    fun initSuccessState() {
        _uiState.value = _uiState.value.copy(
            isSuccess = null,
        )
    }

    fun removeImage(index: Int) {
        uriState.removeAt(index)
        if (index < existImagesUri.size) {
            existImagesUri.removeAt(index)
        } else {
            resizedImageFiles.removeAt(index - existImagesUri.size)
        }
    }
}
