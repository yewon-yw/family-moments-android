package io.familymoments.app.feature.updatecycle.uistate

data class UpdateCycleUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = "",
    val isOwner: Boolean = true,
    val uploadCycle: Int? = 1
)
