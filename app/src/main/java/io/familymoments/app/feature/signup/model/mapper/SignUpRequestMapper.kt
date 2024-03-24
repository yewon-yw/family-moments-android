package io.familymoments.app.feature.signup.model.mapper

import io.familymoments.app.feature.signup.model.request.SignUpRequest
import io.familymoments.app.feature.signup.model.uistate.SignUpInfoUiState

fun SignUpInfoUiState.toRequest() =
    SignUpRequest(
        id = id,
        password = password,
        name = name,
        email = email,
        strBirthDate = birthDay,
        nickname = nickname
    )
