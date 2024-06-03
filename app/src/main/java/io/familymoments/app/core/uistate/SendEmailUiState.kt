package io.familymoments.app.core.uistate

data class SendEmailUiState(
    val isSuccess: Boolean? = null,
    val isLoading:Boolean = false,
    val result:String = "",
    val message: String = ""
)
