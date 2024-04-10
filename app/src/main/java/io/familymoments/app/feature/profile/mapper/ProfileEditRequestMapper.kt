package io.familymoments.app.feature.profile.mapper

import io.familymoments.app.feature.profile.uistate.ProfileEditInfoUiState

fun ProfileEditInfoUiState.toRequest() =
    io.familymoments.app.core.network.dto.request.ProfileEditRequest(
        name = name,
        nickname = nickname,
        birthdate = birthdate
    )
