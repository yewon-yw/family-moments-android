package io.familymoments.app.feature.addpost.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.core.network.util.createImageMultiPart
import io.familymoments.app.feature.addpost.AddPostMode
import io.familymoments.app.feature.addpost.uistate.AddPostUiState
import io.familymoments.app.feature.addpost.uistate.ExistPostUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val mode: Int = savedStateHandle[Route.EditPost.modeArg] ?: AddPostMode.ADD.mode
    private val editPostId: Long = savedStateHandle[Route.EditPost.editPostIdArg] ?: 0
    private val editImages: Array<String> = savedStateHandle[Route.EditPost.editImagesArg] ?: arrayOf()
    private val editContent: String = savedStateHandle[Route.EditPost.editContentArg] ?: ""

    private val _uiState = MutableStateFlow(
        AddPostUiState(
            mode = when (mode) {
                AddPostMode.ADD.mode -> AddPostMode.ADD
                else -> AddPostMode.EDIT
            },
            existPostUiState = ExistPostUiState(
                editPostId = editPostId,
                editImages = editImages.toList(),
                editContent = editContent
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        // 문자열에서 공백, 대괄호 제거
        val regex = Regex("[\\[\\] ]")
        val editImages = this.editImages.getOrNull(0)?.replace(regex, "")?.split(",") ?: listOf()
        _uiState.update {
            it.copy(existPostUiState = it.existPostUiState.copy(editImages = editImages))
        }
    }

    suspend fun addPost(content: String, files: List<File>) {
        val imagesMultipart = files.map { file ->
            createImageMultiPart(file, "imgs")
        }
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.addPost(familyId, content, imagesMultipart)
            },
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

    fun editPost(index: Long, content: String, files: List<File>) {
        async(
            operation = {
                val imagesMultipart = files.map { file ->
                    createImageMultiPart(file, "imgs")
                }
                postRepository.editPost(index, content, imagesMultipart)
            },
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

    fun initSuccessState() {
        _uiState.value = _uiState.value.copy(
            isSuccess = null,
        )
    }
}
