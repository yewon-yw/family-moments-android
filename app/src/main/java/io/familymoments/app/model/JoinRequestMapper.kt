package io.familymoments.app.model

fun JoinInfoUiModel.toRequest() =
        JoinRequest(
                id = id,
                passwordA = password,
                passwordB = password,
                name = name,
                email = email,
                strBirthDate = birthDay,
                nickname = nickname)