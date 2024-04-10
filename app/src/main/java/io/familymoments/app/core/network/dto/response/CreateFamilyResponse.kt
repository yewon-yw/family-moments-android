package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class CreateFamilyResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: CreateFamilyResult = CreateFamilyResult()
)

data class CreateFamilyResult(
    val familyId: Long = 0L,
    val ownerNickName: String = "",
    val inviteCode: String = ""
)
