package io.familymoments.app.feature.transferpermission.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.dto.request.TransferPermissionRequest
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.transferpermission.uistate.TransferPermissionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TransferPermissionViewModel @Inject constructor(
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(TransferPermissionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkFamilyPermission()
        getFamilyMember()
    }

    private fun checkFamilyPermission() {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.checkFamilyPermission(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    checkSuccess = true,
                    isOwner = it
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    checkSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    private fun getFamilyMember() {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.getFamilyMember(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    getSuccess = true,
                    members = it
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    getSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun transferPermission(userId: String) {
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.transferPermission(familyId, TransferPermissionRequest(userId))
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    transferSuccess = true
                )
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    transferSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }
}
