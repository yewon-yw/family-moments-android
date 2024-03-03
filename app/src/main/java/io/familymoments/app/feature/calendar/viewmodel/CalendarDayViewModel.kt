package io.familymoments.app.feature.calendar.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.calendar.model.CalendarDayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarDayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val initialLocalDate = LocalDate.parse(savedStateHandle.get<String>(Route.CalendarDay.localDateStringArgs))

    private val _calendarDayUiState = MutableStateFlow(CalendarDayUiState(selectedDate = initialLocalDate))
    val calendarDayUiState = _calendarDayUiState.asStateFlow()

    private var minPostId: Long = 0

    init {
        getPostsByDay()
    }

    private fun getPostsByDay() {
        Timber.d("getPostsByDay")
        val selectedDate = _calendarDayUiState.value.selectedDate
        val year = selectedDate.year
        val month = selectedDate.monthValue
        val day = selectedDate.dayOfMonth
        async(
            //TODO: familyId 수정예정
            operation = { postRepository.getPostsByDay(2, year, month, day) },
            onSuccess = {
                _calendarDayUiState.value = _calendarDayUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    posts = it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                _calendarDayUiState.value = _calendarDayUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    fun loadMorePostsByDay() {
        Timber.d("loadMorePostsByDay")
        val selectedDate = _calendarDayUiState.value.selectedDate
        val year = selectedDate.year
        val month = selectedDate.monthValue
        val day = selectedDate.dayOfMonth
        async(
            //TODO: familyId 수정예정
            operation = { postRepository.loadMorePostsByDay(2, year, month, day, minPostId) },
            onSuccess = {
                _calendarDayUiState.value = _calendarDayUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    posts = _calendarDayUiState.value.posts + it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                _calendarDayUiState.value = _calendarDayUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    fun getPostsByPrevDay() {
        Timber.d("getPostsByPrevDay")
        val selectedDate = _calendarDayUiState.value.selectedDate.minusDays(1)
        _calendarDayUiState.value = _calendarDayUiState.value.copy(selectedDate = selectedDate)
        getPostsByDay()
    }

    fun getPostsByNextDay() {
        Timber.d("getPostsByNextDay")
        val selectedDate = _calendarDayUiState.value.selectedDate.plusDays(1)
        _calendarDayUiState.value = _calendarDayUiState.value.copy(selectedDate = selectedDate)
        getPostsByDay()
    }
}
