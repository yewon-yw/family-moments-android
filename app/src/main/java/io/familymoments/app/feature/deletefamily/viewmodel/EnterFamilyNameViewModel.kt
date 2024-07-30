package io.familymoments.app.feature.deletefamily.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.feature.deletefamily.uistate.EnterFamilyNameUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterFamilyNameViewModel @Inject constructor(
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(EnterFamilyNameUiState())
    val uiState = _uiState.asStateFlow()

    fun deleteFamily() {
        showLoading()
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                familyRepository.deleteFamily(familyId)
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(isSuccess = true)
                viewModelScope.launch(Dispatchers.IO) {
                    userInfoPreferencesDataSource.removeFamilyId()
                }
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false,
                    errorMessage = it.message
                )
            }
        )
    }

    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = null)
    }
}
