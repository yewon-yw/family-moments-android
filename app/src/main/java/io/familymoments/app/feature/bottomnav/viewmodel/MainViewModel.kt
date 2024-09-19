package io.familymoments.app.feature.bottomnav.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.HttpResponseMessage
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.util.DEFAULT_FAMILY_ID_VALUE
import io.familymoments.app.core.util.DEFAULT_TOKEN_VALUE
import io.familymoments.app.core.util.EventManager
import io.familymoments.app.core.util.UserEvent
import io.familymoments.app.feature.bottomnav.uistate.AppBarUiState
import io.familymoments.app.feature.bottomnav.uistate.MainUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _familyUiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val familyUiState: StateFlow<MainUiState> = _familyUiState.asStateFlow()

    init {
        getProfileImg()
        getFamilyName()
        getFamilyMember()
        checkFamilyExist()

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
            onFailure = {
                if (it.message == HttpResponseMessage.USER_NOT_IN_FAMILY_404 || it.message == HttpResponseMessage.FAMILY_NOT_EXIST_404) {
                    _familyUiState.value = MainUiState(
                        familyExist = false
                    )
                }
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
                _appBarUiState.value = _appBarUiState.value.copy(
                    familyMember = it.result
                )
            },
            onFailure = {}
        )
    }

    private fun checkFamilyExist() {
        viewModelScope.launch {
            val accessToken = userInfoPreferencesDataSource.loadAccessToken()
            val familyId = userInfoPreferencesDataSource.loadFamilyId()
            if (accessToken != DEFAULT_TOKEN_VALUE && familyId == DEFAULT_FAMILY_ID_VALUE) {
                _familyUiState.value = MainUiState(
                    familyExist = false
                )
            }
        }
    }

    fun reportUser(userId: String) {
        async(
            operation = {
                userRepository.reportUser(userId)
            },
            onSuccess = {
                _appBarUiState.value = _appBarUiState.value.copy(reportSuccess = true)
            },
            onFailure = {
                _appBarUiState.value = _appBarUiState.value.copy(reportSuccess = false)
            }
        )
    }

    fun resetReportSuccess() {
        _appBarUiState.value = _appBarUiState.value.copy(reportSuccess = null)
    }
}
