package io.familymoments.app.feature.modifypassword.model.request

data class ModifyPasswordRequest(
    val password: String = "",
    val newPassword_first: String = "",
    val newPassword: String = ""
)
