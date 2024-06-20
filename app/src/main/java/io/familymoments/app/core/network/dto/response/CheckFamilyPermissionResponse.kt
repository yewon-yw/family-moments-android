package io.familymoments.app.core.network.dto.response

data class CheckFamilyPermissionResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String? = "",
    val result: FamilyPermission = FamilyPermission()
)

data class FamilyPermission(
    val isOwner: Boolean = false
)
