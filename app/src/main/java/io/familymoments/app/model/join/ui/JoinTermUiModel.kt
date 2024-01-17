package io.familymoments.app.model.join.ui

import io.familymoments.app.ui.component.CheckedStatus

// 회원가입 약관 data class
data class JoinTermUiModel(val isEssential: Boolean, val description: Int, val checkedStatus: CheckedStatus)