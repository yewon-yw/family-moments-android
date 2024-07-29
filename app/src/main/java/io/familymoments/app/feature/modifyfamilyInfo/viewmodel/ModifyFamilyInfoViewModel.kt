package io.familymoments.app.feature.modifyfamilyInfo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.util.createImageMultiPart
import io.familymoments.app.core.util.EventManager
import io.familymoments.app.core.util.FileUtil
import io.familymoments.app.core.util.UserEvent
import io.familymoments.app.feature.modifyfamilyInfo.model.ModifyFamilyInfoRequest
import io.familymoments.app.feature.modifyfamilyInfo.uistate.ModifyFamilyInfoUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyFamilyInfoViewModel @Inject constructor(
    private val eventManager: EventManager,
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<ModifyFamilyInfoUiState> = MutableStateFlow(ModifyFamilyInfoUiState())
    val uiState: StateFlow<ModifyFamilyInfoUiState> = _uiState.asStateFlow()
    private var index: Int = 0

    init {
        checkFamilyPermission()
        getFamilyInfo()
    }

    // 파일 index 업데이트
    private fun updateIndex() {
        index = 1 - index
    }

    fun updateRepresentImg(context: Context, uri: Uri) {
        try {
            updateIndex()
            val file = FileUtil.imageFileResize(context, uri, index)
            _uiState.value = _uiState.value.copy(
                representImg = file
            )
        } catch (e: Exception) {
            Timber.e("image resize error ${e.message}")
        }
    }

    private fun checkFamilyPermission() {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.checkFamilyPermission(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    isOwner = it.result.isOwner
                )
            },
            onFailure = {}
        )
    }

    private fun getFamilyInfo() {
        showLoading()
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.getFamilyInfo(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    getSuccess = true,
                    familyName = it.familyName
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val representImgFile = FileUtil.uriToFile(Uri.parse(it.representImg), 0)
                    _uiState.value = _uiState.value.copy(
                        representImg = representImgFile
                    )
                }
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    getSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun modifyFamilyInfo(representImgFile: File, familyName: String) {
        async(
            operation = {
                val imageMultipart = createImageMultiPart(representImgFile, "representImg")
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.modifyFamilyInfo(
                    familyId = familyId,
                    representImg = imageMultipart,
                    modifyFamilyInfoRequest = ModifyFamilyInfoRequest(familyName)
                )
            },
            onSuccess = {
                viewModelScope.launch {
                    eventManager.sendEvent(UserEvent.FamilyNameChanged)
                }
                _uiState.value = uiState.value.copy(
                    postSuccess = true,
                )
            },
            onFailure = {
                _uiState.value = uiState.value.copy(
                    postSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun resetGetFamilyInfoIsSuccess() {
        _uiState.value = _uiState.value.copy(getSuccess = null)
    }

    fun resetPostFamilyInfoIsSuccess() {
        _uiState.value = _uiState.value.copy(postSuccess = null)
    }
}
