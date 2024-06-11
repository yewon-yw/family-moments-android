package io.familymoments.app.feature.profile.uistate

import androidx.compose.runtime.Immutable
import java.io.File

@Immutable
data class ProfileEditUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val profileEditInfoUiState: ProfileEditInfoUiState = ProfileEditInfoUiState(),
    val profileEditValidated: ProfileEditValidated = ProfileEditValidated(),
    val profileImage: File? = null
)

data class ProfileEditInfoUiState(
    val name: String = "",
    val nickname: String = "",
    val birthdate: String = ""
)

data class ProfileEditValidated(
    val nameValidated: Boolean = true,
    val nicknameValidated: Boolean = true,
    val birthdateValidated: Boolean = true
)
