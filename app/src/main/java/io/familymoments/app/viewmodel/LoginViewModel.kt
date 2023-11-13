package io.familymoments.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _loginState = MutableStateFlow<LoginResponse?>(null)
    val loginState: StateFlow<LoginResponse?> = _loginState.asStateFlow()

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(username, password).collect { response ->
                _loginState.value = response
            }
        }
    }
}