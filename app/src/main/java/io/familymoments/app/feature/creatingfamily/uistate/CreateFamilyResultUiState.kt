package io.familymoments.app.feature.creatingfamily.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class CreateFamilyResultUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = "",
    val result: io.familymoments.app.core.network.dto.response.CreateFamilyResult = io.familymoments.app.core.network.dto.response.CreateFamilyResult()
)
