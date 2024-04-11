package io.familymoments.app.core.network.dto.request

data class ModifyPasswordRequest(
    val password: String = "",
    val newPassword_first: String = "",
    val newPassword: String = ""
)
