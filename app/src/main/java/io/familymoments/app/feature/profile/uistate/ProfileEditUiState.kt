package io.familymoments.app.feature.profile.uistate

import androidx.compose.runtime.Immutable
import java.io.File

@Immutable
data class ProfileEditUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val nickname: String = "",
    val nicknameValidated: Boolean = true,
    val profileImage: File? = null
)
