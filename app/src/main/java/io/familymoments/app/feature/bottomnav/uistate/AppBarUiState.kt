package io.familymoments.app.feature.bottomnav.uistate

import io.familymoments.app.core.network.dto.response.Member

data class AppBarUiState(
    val profileImgUrl: String = "",
    val familyName: String = "",
    val familyMember: List<Member> = emptyList()
)
