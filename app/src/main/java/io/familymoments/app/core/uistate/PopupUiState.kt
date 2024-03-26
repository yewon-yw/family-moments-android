package io.familymoments.app.core.uistate

data class PopupUiState(
    val completePopupUiState: CompletePopupUiState = CompletePopupUiState(),
    val deletePopupUiState: DeletePopupUiState = DeletePopupUiState(),
    val reportPopupUiState: ReportPopupUiState = ReportPopupUiState(),
    val popupStatusLogics: PopupStatusLogics = PopupStatusLogics()
)

data class PopupStatusLogics(
    val showCompletePopup: (Boolean) -> Unit = {},
    val showDeletePopup: (Boolean, String, () -> Unit) -> Unit = { _, _, _ -> },
    val showReportPopup: (Boolean, () -> Unit) -> Unit = { _, _ -> },
)

data class DeletePopupUiState(
    val show: Boolean = false,
    val content: String = "",
    val execute: () -> Unit = {}
)

data class ReportPopupUiState(
    val show: Boolean = false,
    val execute: () -> Unit = {}
)

data class CompletePopupUiState(
    val show: Boolean = false
)
