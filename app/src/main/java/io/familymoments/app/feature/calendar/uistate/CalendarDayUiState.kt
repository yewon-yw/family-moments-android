package io.familymoments.app.feature.calendar.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.Post
import io.familymoments.app.feature.home.uistate.PostPopupType
import java.time.LocalDate

@Immutable
data class CalendarDayUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val posts: List<Post> = emptyList(),
    val popup:PostPopupType? = null
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST && posts.isEmpty()

    companion object {
        private const val NO_POST = "post가 존재하지 않습니다."
    }
}
