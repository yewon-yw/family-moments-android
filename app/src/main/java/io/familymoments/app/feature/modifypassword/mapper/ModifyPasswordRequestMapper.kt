package io.familymoments.app.feature.modifypassword.mapper

import io.familymoments.app.core.network.dto.request.ModifyPasswordRequest
import io.familymoments.app.feature.modifypassword.uistate.ModifyPasswordUiState

fun ModifyPasswordUiState.toRequest() =
    ModifyPasswordRequest(
        password = currentPasswordUiState.currentPassword,
        newPassword_first = newPasswordUiState.newPassword,
        newPassword = newPasswordCheckUiState.newPasswordCheck
    )
