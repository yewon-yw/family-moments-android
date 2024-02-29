package io.familymoments.app.feature.modifypassword.model

import io.familymoments.app.R

enum class WarningType(val stringResId: Int) {
    IncorrectCurrentPassword(R.string.modify_password_incorrect_current_password_warning),
    NewPasswordsMismatch(R.string.modify_password_new_passwords_mismatch_warning),
    NewPasswordSameAsCurrent(R.string.modify_password_new_password_same_as_current_warning),
    InvalidPasswordFormat(R.string.modify_password_invalid_password_format_warning)
}
