package io.familymoments.app.feature.transferpermission.uistate

import io.familymoments.app.core.network.dto.response.Member

data class TransferPermissionUiState(
    val getSuccess: Boolean = false,
    val transferSuccess: Boolean = false,
    val errorMessage: String? = null,
    val members: List<Member> = emptyList()
)
