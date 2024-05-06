package io.familymoments.app.feature.forgotpassword.uistate

data class VerifyUiState(
    val sendEmailUiState: SendEmailUiState = SendEmailUiState()
)

data class SendEmailUiState(
    val isSuccess: Boolean? = null,
    val result:String = "",
    val message: String = ""
)
