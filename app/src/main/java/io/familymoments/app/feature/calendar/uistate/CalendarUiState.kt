package io.familymoments.app.feature.calendar.uistate

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.YearMonth

@Immutable
data class CalendarUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val today: LocalDate = LocalDate.now(),
    val yearMonth: YearMonth = YearMonth.now(),
    val dates: List<LocalDate> = emptyList(),
    val postResult: List<String> = emptyList()
) {
    val startDate: LocalDate = if (dates.isNotEmpty()) dates.first { it != LocalDate.MIN } else LocalDate.now()
    val endDate: LocalDate = if (dates.isNotEmpty()) dates.last { it != LocalDate.MIN } else LocalDate.now()

    val isTodayInMonth: Boolean = yearMonth.year == LocalDate.now().year && yearMonth.month == LocalDate.now().month
}
