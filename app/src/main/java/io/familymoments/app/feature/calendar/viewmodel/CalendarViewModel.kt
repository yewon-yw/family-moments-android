package io.familymoments.app.feature.calendar.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.feature.calendar.model.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : BaseViewModel() {

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState = _calendarUiState.asStateFlow()

    init {
        getTodayCalendar()
    }

    private fun getTodayCalendar() {
        val yearMonth = YearMonth.now()
        val firstOfMonth = yearMonth.atDay(1)
        val firstOfFollowingMonth = yearMonth.plusMonths(1).atDay(1)
        _calendarUiState.value = CalendarUiState(
            yearMonth = yearMonth,
            dates = getDatesBetween(
                firstOfMonth,
                firstOfFollowingMonth
            )
        )
    }

    fun getPreviousMonth() {
        val firstDayOfMonth = _calendarUiState.value.startDate
        val prevDay = firstDayOfMonth.minusDays(1)
        val prevMonth = YearMonth.of(prevDay.year, prevDay.month)
        _calendarUiState.value = CalendarUiState(
            yearMonth = prevMonth,
            dates = getDatesInBetween(
                prevMonth.atDay(1),
                firstDayOfMonth
            )
        )
    }

    fun getNextMonth() {
        val lastDayOfMonth = _calendarUiState.value.endDate
        val nextDay = lastDayOfMonth.plusDays(1)
        val nextMonth = YearMonth.of(nextDay.year, nextDay.month)
        _calendarUiState.value = CalendarUiState(
            yearMonth = nextMonth,
            dates = getDatesBetween(
                nextMonth.atDay(1),
                nextMonth.plusMonths(1).atDay(1)
            )
        )
    }

    private fun getDatesInBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }.limit(numOfDays).collect(Collectors.toList())
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }.limit(numOfDays).collect(Collectors.toList())
    }
}
