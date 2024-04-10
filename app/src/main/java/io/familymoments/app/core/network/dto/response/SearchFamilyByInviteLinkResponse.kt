package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class SearchFamilyByInviteLinkResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: SearchFamilyByInviteLinkResult? = null
)

@Immutable
data class SearchFamilyByInviteLinkResult(
    val familyId: Long = 0,
    val owner: String = "",
    val familyName: String = "",
    val uploadCycle: Int? = null,
    val inviteCode: String = "",
    val representImg: String = ""
)
