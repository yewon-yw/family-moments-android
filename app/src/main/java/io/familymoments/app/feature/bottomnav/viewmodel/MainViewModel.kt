package io.familymoments.app.feature.bottomnav.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSourceImpl.Companion.DEFAULT_FAMILY_ID
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSourceImpl.Companion.DEFAULT_TOKEN_VALUE
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.bottomnav.model.uistate.MainUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _mainUiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    init {
        checkFamilyExist()
    }

    fun reissueAccessToken(suspendFunction: suspend () -> Flow<Resource<*>>) {
        async(
            operation = { userRepository.reissueAccessToken() },
            onSuccess = {
                viewModelScope.launch {
                    async(
                        operation = suspendFunction,
                        onSuccess = {},
                        onFailure = {}
                    )
                }
            },
            onFailure = {}
        )
    }

    private fun checkFamilyExist() {
        viewModelScope.launch {
            val accessToken = userInfoPreferencesDataSource.loadAccessToken()
            val familyId = userInfoPreferencesDataSource.loadFamilyId()
            if (accessToken != DEFAULT_TOKEN_VALUE && familyId == DEFAULT_FAMILY_ID) {
                _mainUiState.value = MainUiState(
                    familyExist = false
                )
            } else {
                _mainUiState.value = MainUiState(
                    familyExist = true
                )
            }
        }
    }
}
