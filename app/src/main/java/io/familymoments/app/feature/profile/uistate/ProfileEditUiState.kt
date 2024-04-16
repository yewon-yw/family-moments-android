package io.familymoments.app.feature.profile.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileEditUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val profileEditInfoUiState: ProfileEditInfoUiState = ProfileEditInfoUiState(),
    val profileEditValidated: ProfileEditValidated = ProfileEditValidated(),
    val profileImage: ProfileImage
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

sealed class ProfileImage {
    data class Url(val imgUrl: String) : ProfileImage()
    data class Bitmap(val bitmap: android.graphics.Bitmap) : ProfileImage()
}
