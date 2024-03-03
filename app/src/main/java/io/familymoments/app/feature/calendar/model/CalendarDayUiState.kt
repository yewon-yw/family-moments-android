package io.familymoments.app.feature.calendar.model

import io.familymoments.app.feature.home.model.Post
import java.time.LocalDate

data class CalendarDayUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val posts: List<Post> = emptyList()
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST && posts.isEmpty()

    companion object {
        private const val NO_POST = "post가 존재하지 않습니다."
    }
}
