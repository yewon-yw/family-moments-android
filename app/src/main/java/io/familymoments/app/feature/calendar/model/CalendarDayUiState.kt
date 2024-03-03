package io.familymoments.app.feature.calendar.model

import io.familymoments.app.feature.home.model.Post
import java.time.LocalDate

data class CalendarDayUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val posts: List<Post> = emptyList()
)
