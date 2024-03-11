package io.familymoments.app.feature.modifypassword

import androidx.annotation.StringRes
import io.familymoments.app.feature.modifypassword.model.WarningType

fun validateCurrentPassword(password: String): Boolean {
    return password.isNotEmpty()
}

fun validateNewPassword(newPassword: String, newPasswordCheck: String): Pair<Boolean, Int?> {
    var validation = false
    @StringRes var warningResId: Int? = null
    if (newPassword.isEmpty() && newPasswordCheck.isEmpty()) {
        warningResId = null
    } else if (!checkPasswordFormat(newPassword)) {
        warningResId = WarningType.InvalidPasswordFormat.stringResId
    } else if(newPasswordCheck.isEmpty()) {
        warningResId = null
    } else if (newPassword != newPasswordCheck) {
        warningResId = WarningType.NewPasswordsMismatch.stringResId
    } else {
        validation = true
        warningResId = null
    }
    return validation to warningResId
}

private fun checkPasswordFormat(password: String): Boolean {
    return password.matches(ModifyPasswordCheck.passwordRegex)
}

private object ModifyPasswordCheck{
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{8,12}\$")
}
