package io.familymoments.app.feature.deletefamily.uistate

data class DeleteFamilyUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = "",
    val familyName: String = ""
)
