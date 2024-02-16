package io.familymoments.app.model.uistate

import io.familymoments.app.model.response.LoginResult

data class LoginUiState(
    val isLoading: Boolean? = null,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val loginResult: LoginResult? = null,
)
