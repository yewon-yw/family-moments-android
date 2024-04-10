package io.familymoments.app.feature.creatingfamily.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.Member

@Immutable
data class SearchMemberUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = "",
    val members: List<io.familymoments.app.core.network.dto.response.Member> = listOf()
)
