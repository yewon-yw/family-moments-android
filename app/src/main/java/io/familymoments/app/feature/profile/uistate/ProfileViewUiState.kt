package io.familymoments.app.feature.profile.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.UserProfile

@Immutable
data class ProfileViewUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val userProfile: io.familymoments.app.core.network.dto.response.UserProfile = io.familymoments.app.core.network.dto.response.UserProfile()
)
