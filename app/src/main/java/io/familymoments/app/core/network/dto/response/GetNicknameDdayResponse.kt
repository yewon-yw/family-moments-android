package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class GetNicknameDdayResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: NicknameDdayResult = NicknameDdayResult()
)

data class NicknameDdayResult(
    val nickname: String = "",
    val createdAt: String = ""
)
