package io.familymoments.app.feature.calendar.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.calendar.model.CalendarDayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarDayViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _calendarDayUiState = MutableStateFlow(CalendarDayUiState())
    val calendarDayUiState = _calendarDayUiState.asStateFlow()

    private var minPostId: Long = 0

    init {
        getPostsByDay()
    }

    private fun getPostsByDay() {
        async(
            operation = { postRepository.getPostsByDay(2, 2024, 1, 11) },
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
}
