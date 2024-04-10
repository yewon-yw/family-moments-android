package io.familymoments.app.feature.signup.mapper

import io.familymoments.app.core.network.dto.request.SignUpRequest
import io.familymoments.app.feature.signup.uistate.SignUpInfoUiState

fun SignUpInfoUiState.toRequest() =
    io.familymoments.app.core.network.dto.request.SignUpRequest(
        id = id,
        password = password,
        name = name,
        email = email,
        strBirthDate = birthDay,
        nickname = nickname
    )
