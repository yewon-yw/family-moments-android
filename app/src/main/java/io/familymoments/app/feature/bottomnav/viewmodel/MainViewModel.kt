package io.familymoments.app.feature.bottomnav.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.bottomnav.model.AppBarUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _appBarUiState = MutableStateFlow(AppBarUiState())
    val appBarUiState = _appBarUiState.asStateFlow()

    init {
        getProfileImg()
    }

    private fun getProfileImg() {
        viewModelScope.launch {
            userInfoPreferencesDataSource.loadUserProfileImg().let {
                _appBarUiState.value = _appBarUiState.value.copy(
                    profileImgUrl = it
                )
            }
        }
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
}
