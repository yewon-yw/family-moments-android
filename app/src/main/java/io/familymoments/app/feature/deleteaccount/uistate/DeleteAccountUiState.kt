package io.familymoments.app.feature.deleteaccount.uistate

data class DeleteAccountUiState(
    val showPopup: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val result:String = ""
)
