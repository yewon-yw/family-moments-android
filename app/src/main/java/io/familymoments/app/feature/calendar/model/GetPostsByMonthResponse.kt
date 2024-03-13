package io.familymoments.app.feature.calendar.model

data class GetPostsByMonthResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<String> = emptyList()
)
