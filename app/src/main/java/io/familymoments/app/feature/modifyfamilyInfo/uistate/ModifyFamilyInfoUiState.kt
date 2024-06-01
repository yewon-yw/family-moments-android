package io.familymoments.app.feature.modifyfamilyInfo.uistate

import java.io.File

data class ModifyFamilyInfoUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val familyName: String = "",
    val representImg: File? = null
)
