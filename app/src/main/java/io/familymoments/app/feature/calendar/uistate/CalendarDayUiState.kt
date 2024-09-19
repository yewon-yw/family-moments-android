package io.familymoments.app.feature.calendar.uistate

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.dto.response.PostResult
import io.familymoments.app.feature.home.uistate.PostPopupType
import java.time.LocalDate

@Immutable
data class CalendarDayUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val posts: List<PostResult> = emptyList(),
    val popup:PostPopupType? = null,
    val userNickname:String = ""
) {
    val hasNoPost = posts.isEmpty()
}
