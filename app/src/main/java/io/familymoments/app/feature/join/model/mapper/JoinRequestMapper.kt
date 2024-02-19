package io.familymoments.app.feature.join.model.mapper

import io.familymoments.app.feature.join.model.request.JoinRequest
import io.familymoments.app.feature.join.model.JoinInfoUiModel

fun JoinInfoUiModel.toRequest() =
    JoinRequest(
        id = id,
        passwordA = password,
        passwordB = password,
        name = name,
        email = email,
        strBirthDate = birthDay,
        nickname = nickname
    )
