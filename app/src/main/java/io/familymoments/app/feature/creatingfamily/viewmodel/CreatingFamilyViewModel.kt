package io.familymoments.app.feature.creatingfamily.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.dto.request.CreateFamilyRequest
import io.familymoments.app.core.network.dto.request.FamilyProfile
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.creatingfamily.uistate.CreateFamilyResultUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class CreatingFamilyViewModel @Inject constructor(
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _familyProfile: MutableStateFlow<FamilyProfile> =
        MutableStateFlow(
            FamilyProfile()
        )
    val familyProfile: StateFlow<FamilyProfile> =
        _familyProfile.asStateFlow()

    private val _createFamilyResultUiState: MutableStateFlow<CreateFamilyResultUiState> = MutableStateFlow(
        CreateFamilyResultUiState()
    )
    val createFamilyResultUiState: StateFlow<CreateFamilyResultUiState> = _createFamilyResultUiState.asStateFlow()

    fun saveFamilyProfile(familyProfile: FamilyProfile) {
        _familyProfile.value = familyProfile
    }

    fun createFamily(familyProfile: FamilyProfile) {
        check(familyProfile.imgFile != null) {
            throw NullPointerException()
        }
        val imageRequestBody = familyProfile.imgFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart =
            MultipartBody.Part.createFormData("representImg", familyProfile.imgFile.name, imageRequestBody)
        val createFamilyRequest = CreateFamilyRequest(
            familyName = familyProfile.name,
            uploadCycle = familyProfile.uploadCycle
        )
        async(
            operation = { familyRepository.createFamily(profileImgPart, createFamilyRequest) },
            onSuccess = {
                _createFamilyResultUiState.value =
                    _createFamilyResultUiState.value.copy(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        result = it.result
                    )
                viewModelScope.launch {
                    userInfoPreferencesDataSource.saveFamilyId(it.result.familyId)
                }
            },
            onFailure = {
                _createFamilyResultUiState.value =
                    _createFamilyResultUiState.value.copy(
                        isSuccess = false,
                        isLoading = isLoading.value,
                        errorMessage = it.message
                    )
            }
        )
    }
}
