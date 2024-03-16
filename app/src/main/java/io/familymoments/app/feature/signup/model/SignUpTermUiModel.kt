package io.familymoments.app.feature.signup.model

import io.familymoments.app.core.component.CheckedStatus

// 회원가입 약관 data class
data class SignUpTermUiModel(
    val isEssential: Boolean,
    val description: Int,
    val checkedStatus: CheckedStatus
)
