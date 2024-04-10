package io.familymoments.app.feature.addpost.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.feature.addpost.AddPostMode

@Immutable
data class AddPostUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val mode: AddPostMode = AddPostMode.ADD,
    val existPostUiState: ExistPostUiState = ExistPostUiState()
)

@Immutable
data class ExistPostUiState(
    val editPostId: Int = 0,
    val editImages: List<String> = listOf(),
    val editContent: String = ""
)
