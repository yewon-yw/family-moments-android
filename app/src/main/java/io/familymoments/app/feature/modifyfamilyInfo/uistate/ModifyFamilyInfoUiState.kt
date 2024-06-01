package io.familymoments.app.feature.modifyfamilyInfo.uistate

import java.io.File

data class ModifyFamilyInfoUiState(
    val getSuccess: Boolean? = null,
    val postSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val familyName: String = "",
    val representImg: File? = null
)
