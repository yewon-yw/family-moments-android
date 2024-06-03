package io.familymoments.app.feature.forgotpassword.uistate

import io.familymoments.app.core.uistate.SendEmailUiState

data class VerifyPwdUiState(
    val sendEmailUiState: SendEmailUiState = SendEmailUiState(),
    val findPwdUiState: FindPwdUiState = FindPwdUiState()
)

data class FindPwdUiState(
    val isSuccess: Boolean? = null,
    val result:String = "",
    val message: String = ""
)
