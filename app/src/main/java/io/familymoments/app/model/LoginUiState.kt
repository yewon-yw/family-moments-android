package io.familymoments.app.model

data class LoginUiState(
        val isLoading: Boolean? = null,
        val isSuccess: Boolean? = null,
        val errorMessage: String? = null,
        val loginResult: LoginResult? = null,
)