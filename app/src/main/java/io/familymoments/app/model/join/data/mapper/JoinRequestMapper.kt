package io.familymoments.app.model.join.data.mapper

import io.familymoments.app.model.join.data.request.JoinRequest
import io.familymoments.app.model.join.ui.JoinInfoUiModel

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