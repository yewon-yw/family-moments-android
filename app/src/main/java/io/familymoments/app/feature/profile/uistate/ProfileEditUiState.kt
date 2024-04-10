package io.familymoments.app.feature.profile.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileEditUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val profileEditInfoUiState: ProfileEditInfoUiState = ProfileEditInfoUiState(),
    val profileImage: ProfileImage
)

data class ProfileEditInfoUiState(
    val name: String = "",
    val nickname: String = "",
    val birthdate: String = ""
)

sealed class ProfileImage {
    data class Url(val imgUrl: String) : ProfileImage()
    data class Bitmap(val bitmap: android.graphics.Bitmap) : ProfileImage()
}
