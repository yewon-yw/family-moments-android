package io.familymoments.app.feature.modifypassword

import io.familymoments.app.feature.modifypassword.model.WarningType

fun validateCurrentPassword(password: String): Boolean {
    return password.isNotEmpty()
}

fun validateNewPasswordFormat(password: String): Pair<Boolean, Int?> {
    var validation = false
    var warningResId: Int? = null
    if (password.isNotEmpty()) {
        if (password.matches(ModifyPasswordCheck.passwordRegex)) {
            validation = true
        } else {
            warningResId = WarningType.InvalidPasswordFormat.stringResId
        }
    }
    return validation to warningResId
}

fun validateNewPasswordEqual(newPassword: String, newPasswordCheck: String): Pair<Boolean, Int?> {
    var validation = false
    var warningResId: Int? = null
    if (newPasswordCheck.isNotEmpty()) {
        if (newPassword == newPasswordCheck) {
            validation = true
        } else {
            warningResId = WarningType.NewPasswordsMismatch.stringResId
        }
    }
    return validation to warningResId
}

private object ModifyPasswordCheck {
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{8,12}\$")
}
