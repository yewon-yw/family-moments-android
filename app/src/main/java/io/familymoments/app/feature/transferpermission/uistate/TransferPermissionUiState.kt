package io.familymoments.app.feature.transferpermission.uistate

import io.familymoments.app.core.network.dto.response.Member

data class TransferPermissionUiState(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isOwner: Boolean = true,
    val members: List<Member> = emptyList()
)
