package io.familymoments.app.feature.mypage.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class MyPageUiState(
    val logoutUiState: LogoutUiState = LogoutUiState()
)

@Immutable
data class LogoutUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
