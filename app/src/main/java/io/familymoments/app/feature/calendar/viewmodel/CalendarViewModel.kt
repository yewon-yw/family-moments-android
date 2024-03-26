package io.familymoments.app.feature.calendar.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.calendar.model.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState = _calendarUiState.asStateFlow()

    init {
        getTodayCalendar()
    }

    private fun getTodayCalendar() {
        val yearMonth = YearMonth.now()
        val firstOfMonth = yearMonth.atDay(1)
        val firstOfFollowingMonth = yearMonth.plusMonths(1).atDay(1)
        val dates = transformedDates(firstOfMonth, firstOfFollowingMonth)

        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.getPostsByMonth(
                    familyId,
                    yearMonth.year,
                    yearMonth.monthValue
                )
            },
            onSuccess = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    yearMonth = yearMonth,
                    dates = dates,
                    postResult = it.result.distinct() // distinct()로 중복 제거
                )
            },
            onFailure = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message,
                    yearMonth = yearMonth,
                    dates = dates,
                    postResult = emptyList()
                )
            }
        )
    }

    fun getPreviousMonth() {
        val firstDayOfMonth = _calendarUiState.value.startDate
        val prevDay = firstDayOfMonth.minusDays(1)
        val prevMonth = YearMonth.of(prevDay.year, prevDay.month)
        val dates = transformedDates(prevMonth.atDay(1), firstDayOfMonth)

        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.getPostsByMonth(
                    familyId,
                    prevMonth.year,
                    prevMonth.monthValue
                )
            },
            onSuccess = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    yearMonth = prevMonth,
                    dates = dates,
                    postResult = it.result.distinct() // distinct()로 중복 제거
                )
            },
            onFailure = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message,
                    yearMonth = prevMonth,
                    dates = dates,
                    postResult = emptyList()
                )
            }
        )
    }

    fun getNextMonth() {
        val lastDayOfMonth = _calendarUiState.value.endDate
        val nextDay = lastDayOfMonth.plusDays(1)
        val nextMonth = YearMonth.of(nextDay.year, nextDay.month)
        val dates = transformedDates(nextMonth.atDay(1), nextMonth.plusMonths(1).atDay(1))

        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.getPostsByMonth(
                    familyId,
                    nextMonth.year,
                    nextMonth.monthValue
                )
            },
            onSuccess = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    yearMonth = nextMonth,
                    dates = dates,
                    postResult = it.result.distinct() // distinct()로 중복 제거
                )
            },
            onFailure = {
                _calendarUiState.value = _calendarUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message,
                    yearMonth = nextMonth,
                    dates = dates,
                    postResult = emptyList()
                )
            }
        )
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }.limit(numOfDays).collect(Collectors.toList())
    }

    private fun transformedDates(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dates = getDatesBetween(startDate, endDate).toMutableList()
        val firstDay = dates.first()
        when (firstDay.dayOfWeek) {
            DayOfWeek.MONDAY -> {
                dates.add(0, LocalDate.MIN)
            }

            DayOfWeek.TUESDAY -> {
                repeat(2) {
                    dates.add(0, LocalDate.MIN)
                }
            }

            DayOfWeek.WEDNESDAY -> {
                repeat(3) {
                    dates.add(0, LocalDate.MIN)
                }
            }

            DayOfWeek.THURSDAY -> {
                repeat(4) {
                    dates.add(0, LocalDate.MIN)
                }
            }

            DayOfWeek.FRIDAY -> {
                repeat(5) {
                    dates.add(0, LocalDate.MIN)
                }
            }

            DayOfWeek.SATURDAY -> {
                repeat(6) {
                    dates.add(0, LocalDate.MIN)
                }
            }

            else -> {
                // do nothing
            }
        }
        return dates.toList()
    }
}
