package io.familymoments.app.feature.updatecycle.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.updatecycle.uistate.UpdateCycleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UpdateCycleViewModel @Inject constructor(
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
): BaseViewModel() {
    private val _uiState = MutableStateFlow(UpdateCycleUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkFamilyPermission()
        getUploadCycle()
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

    private fun getUploadCycle() {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.getFamilyInfo(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    uploadCycle = it.uploadCycle
                )
            },
            onFailure = { }
        )
    }

    fun updateCycle(uploadCycle: Int) {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.updateCycle(familyId, uploadCycle)
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
}
