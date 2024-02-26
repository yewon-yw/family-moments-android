package io.familymoments.app.feature.calendar.model

import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    val yearMonth: YearMonth = YearMonth.now(),
    val dates: List<LocalDate> = emptyList()
) {
    val startDate: LocalDate = if (dates.isNotEmpty()) dates.first() else LocalDate.now()
    val endDate: LocalDate = if (dates.isNotEmpty()) dates.last() else LocalDate.now()
}
