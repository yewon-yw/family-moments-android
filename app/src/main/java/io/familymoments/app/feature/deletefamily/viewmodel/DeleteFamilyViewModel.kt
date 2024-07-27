package io.familymoments.app.feature.deletefamily.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.deletefamily.uistate.DeleteFamilyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DeleteFamilyViewModel @Inject constructor(
    private val familyRepository: FamilyRepository
): BaseViewModel() {
    private val _uiState = MutableStateFlow(DeleteFamilyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFamilyName()
    }

    private fun getFamilyName() {
        async(
            operation = {
                familyRepository.getFamilyName()
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = true,
                    familyName = it.result
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
