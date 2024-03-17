package io.familymoments.app.feature.bottomnav.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    fun reissueAccessToken() {
        async(
            operation = { userRepository.reissueAccessToken() },
            onSuccess = {},
            onFailure = {}
        )
    }
}
