package io.familymoments.app.core.network.dto.request

data class ProfileEditRequest(
    val name: String = "",
    val nickname: String = "",
    val birthdate: String = ""
)
