package io.familymoments.app.feature.creatingfamily.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.util.convertBitmapToFile
import io.familymoments.app.feature.creatingfamily.model.CreateFamilyRequest
import io.familymoments.app.feature.creatingfamily.model.FamilyProfile
import io.familymoments.app.feature.creatingfamily.model.uistate.CreateFamilyResultUiState
import io.familymoments.app.feature.creatingfamily.model.uistate.SearchMemberUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class CreatingFamilyViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val familyRepository: FamilyRepository
) : BaseViewModel() {

    private val _searchMemberUiState: MutableStateFlow<SearchMemberUiState> = MutableStateFlow(SearchMemberUiState())
    val searchMemberUiState: StateFlow<SearchMemberUiState> = _searchMemberUiState.asStateFlow()

    private val _createFamilyResultUiState:MutableStateFlow<CreateFamilyResultUiState> = MutableStateFlow(
        CreateFamilyResultUiState()
    )
    val createFamilyResultUiState: StateFlow<CreateFamilyResultUiState> = _createFamilyResultUiState.asStateFlow()

    fun searchMember(keyword: String) {
        async(
            operation = { userRepository.searchMember(keyword) },
            onSuccess = {
                _searchMemberUiState.value =
                    _searchMemberUiState.value.copy(isSuccess = true, members = it.result, isLoading = isLoading.value)
            },
            onFailure = {
                _searchMemberUiState.value =
                    _searchMemberUiState.value.copy(
                        isSuccess = false,
                        errorMessage = it.message,
                        isLoading = isLoading.value
                    )
            }
        )
    }

    fun createFamily(familyProfile: FamilyProfile){
        val imageFile =convertBitmapToFile(familyProfile.img)
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val profileImgPart = MultipartBody.Part.createFormData("representImg", imageFile.name, imageRequestBody)
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
