package io.familymoments.app.feature.creatingfamily.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.CreateFamilyResult

@Immutable
data class CreateFamilyResultUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = "",
    val result: CreateFamilyResult = CreateFamilyResult()
)
