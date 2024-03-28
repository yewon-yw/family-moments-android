package io.familymoments.app.feature.joiningfamily.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.joiningfamily.model.JoinFamilyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JoinFamilyViewModel @Inject constructor(
    private val familyRepository: FamilyRepository
) : BaseViewModel() {

    private val _joinFamilyUiState: MutableStateFlow<JoinFamilyUiState> = MutableStateFlow(JoinFamilyUiState())
    val joinFamilyUiState: StateFlow<JoinFamilyUiState> = _joinFamilyUiState.asStateFlow()

    fun searchFamilyByInviteLink(inviteLink: String) {
        async(
            operation = {
                familyRepository.searchFamilyByInviteLink(inviteLink)
            },
            onSuccess = {
                val newSearchFamilyByInviteLinkUiState = _joinFamilyUiState.value.searchFamilyByInviteLinkUiState.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
                _joinFamilyUiState.value = _joinFamilyUiState.value.copy(
                    searchFamilyByInviteLinkUiState = newSearchFamilyByInviteLinkUiState
                )
            },
            onFailure = {
                val newSearchFamilyByInviteLinkUiState = _joinFamilyUiState.value.searchFamilyByInviteLinkUiState.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
                _joinFamilyUiState.value = _joinFamilyUiState.value.copy(
                    searchFamilyByInviteLinkUiState = newSearchFamilyByInviteLinkUiState
                )
            }
        )
    }

    fun joinFamily(familyId: Long) {
        async(
            operation = {
                familyRepository.joinFamily(familyId)
            },
            onSuccess = {
                val newJoinFamilyExecuteUiState = _joinFamilyUiState.value.joinFamilyExecuteUiState.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    result = it.result
                )
                _joinFamilyUiState.value = _joinFamilyUiState.value.copy(
                    joinFamilyExecuteUiState = newJoinFamilyExecuteUiState
                )
            },
            onFailure = {
                val newJoinFamilyExecuteUiState = _joinFamilyUiState.value.joinFamilyExecuteUiState.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
                _joinFamilyUiState.value = _joinFamilyUiState.value.copy(
                    joinFamilyExecuteUiState = newJoinFamilyExecuteUiState
                )
            }
        )
    }

    fun resetJoinFamilyExecuteSuccess(){
        val newJoinFamilyExecuteUiState = _joinFamilyUiState.value.joinFamilyExecuteUiState.copy(
            isSuccess = null
        )
        _joinFamilyUiState.value = _joinFamilyUiState.value.copy(
            joinFamilyExecuteUiState = newJoinFamilyExecuteUiState
        )
    }
}
