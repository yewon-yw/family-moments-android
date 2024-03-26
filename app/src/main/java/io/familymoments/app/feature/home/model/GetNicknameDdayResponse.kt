package io.familymoments.app.feature.home.model

data class GetNicknameDdayResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: NicknameDdayResult = NicknameDdayResult()
)

data class NicknameDdayResult(
    val nickname: String = "",
    val dday: String = "0"
)
