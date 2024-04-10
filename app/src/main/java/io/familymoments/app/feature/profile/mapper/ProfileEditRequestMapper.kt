package io.familymoments.app.feature.profile.mapper

import io.familymoments.app.core.network.dto.request.ProfileEditRequest
import io.familymoments.app.feature.profile.uistate.ProfileEditInfoUiState

fun ProfileEditInfoUiState.toRequest() =
    ProfileEditRequest(
        name = name,
        nickname = nickname,
        birthdate = birthdate
    )
