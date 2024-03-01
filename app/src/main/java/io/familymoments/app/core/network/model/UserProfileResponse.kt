package io.familymoments.app.core.network.model

data class UserProfileResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: UserProfile = UserProfile()
)

data class UserProfile(
    val name: String = "",
    val birthDate: String = "",
    val profileImg: String = "",
    val nickName: String = "",
    val email: String = "",
    val totalUpload: Int = 0,
    val duration: Int = 0
)
