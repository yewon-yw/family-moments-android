package io.familymoments.app.feature.addpost.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddPostUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
)
