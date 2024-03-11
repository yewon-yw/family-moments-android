package io.familymoments.app.feature.creatingfamily.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.creatingfamily.model.uistate.SearchMemberUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchMemberViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _searchMemberUiState: MutableStateFlow<SearchMemberUiState> = MutableStateFlow(SearchMemberUiState())
    val searchMemberUiState: StateFlow<SearchMemberUiState> = _searchMemberUiState.asStateFlow()

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
}
