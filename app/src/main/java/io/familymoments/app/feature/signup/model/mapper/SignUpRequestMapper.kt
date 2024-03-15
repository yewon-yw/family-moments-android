package io.familymoments.app.feature.signup.model.mapper

import io.familymoments.app.feature.signup.model.SignUpInfoUiModel
import io.familymoments.app.feature.signup.model.request.SignUpRequest

fun SignUpInfoUiModel.toRequest() =
    SignUpRequest(
        id = id,
        passwordA = password,
        passwordB = password,
        name = name,
        email = email,
        strBirthDate = birthDay,
        nickname = nickname
    )
