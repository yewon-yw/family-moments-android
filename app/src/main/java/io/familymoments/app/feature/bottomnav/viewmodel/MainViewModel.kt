package io.familymoments.app.feature.bottomnav.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.util.EventManager
import io.familymoments.app.core.util.UserEvent
import io.familymoments.app.feature.bottomnav.uistate.AppBarUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val eventManager: EventManager,
    private val userRepository: UserRepository,
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _appBarUiState = MutableStateFlow(AppBarUiState())
    val appBarUiState = _appBarUiState.asStateFlow()

    init {
        getProfileImg()
        getFamilyName()

        viewModelScope.launch {
            eventManager.events.collect { event ->
                when (event) {
                    is UserEvent.FamilyNameChanged -> getFamilyName()
                    is UserEvent.ProfileChanged -> getProfileImg()
                }
            }
        }
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

    private fun getFamilyName() {
        async(
            operation = {
                familyRepository.getFamilyName()
            },
            onSuccess = {
                _appBarUiState.value = _appBarUiState.value.copy(
                    familyName = it.result
                )
            },
            onFailure = {}
        )
    }
}
