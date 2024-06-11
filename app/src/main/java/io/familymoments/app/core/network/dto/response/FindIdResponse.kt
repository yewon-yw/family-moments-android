package io.familymoments.app.core.network.dto.response

data class FindIdResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: FindIdResult = FindIdResult()
)

data class FindIdResult(
    val userId: String = ""
)
