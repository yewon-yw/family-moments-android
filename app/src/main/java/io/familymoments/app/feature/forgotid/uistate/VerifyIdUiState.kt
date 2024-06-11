package io.familymoments.app.feature.forgotid.uistate

import io.familymoments.app.core.uistate.SendEmailUiState

data class VerifyIdUiState(
    val sendEmailUiState: SendEmailUiState = SendEmailUiState(),
    val findIdUiState: FindIdUiState = FindIdUiState()
)

data class FindIdUiState(
    val isSuccess: Boolean? = null,
    val userId:String = "",
    val message: String = ""
)
