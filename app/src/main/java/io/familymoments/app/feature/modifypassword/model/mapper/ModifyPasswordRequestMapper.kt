package io.familymoments.app.feature.modifypassword.model.mapper

import io.familymoments.app.feature.modifypassword.model.request.ModifyPasswordRequest
import io.familymoments.app.feature.modifypassword.model.uistate.ModifyPasswordUiState

fun ModifyPasswordUiState.toRequest() =
    ModifyPasswordRequest(
        password = currentPasswordUiState.password,
        newPassword_first = newPasswordUiState.newPassword,
        newPassword = newPasswordCheckUiState.newPasswordCheck
    )
