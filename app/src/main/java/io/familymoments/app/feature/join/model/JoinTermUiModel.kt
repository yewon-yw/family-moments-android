package io.familymoments.app.feature.join.model

import io.familymoments.app.core.component.CheckedStatus

// 회원가입 약관 data class
data class JoinTermUiModel(val isEssential: Boolean, val description: Int, val checkedStatus: CheckedStatus)
