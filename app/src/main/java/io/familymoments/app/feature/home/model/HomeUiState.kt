package io.familymoments.app.feature.home.model

import androidx.compose.runtime.Immutable
import io.familymoments.app.core.network.HttpResponseMessage.NO_POST_404

@Immutable
data class HomeUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val nickname: String = "",
    val dday: String = "",
    val posts: List<Post> = emptyList(),
    val popup: PostPopupType? = null
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST_404 && posts.isEmpty()
}

sealed interface PostPopupType {
    data object PostLovesFailure : PostPopupType
    data object DeleteLovesFailure : PostPopupType
    data class DeletePost(val postId: Long) : PostPopupType
    data object DeletePostSuccess : PostPopupType
    data object DeletePostFailure : PostPopupType
    data class ReportPost(val postId: Long) : PostPopupType
    data object ReportPostSuccess : PostPopupType
    data object ReportPostFailure : PostPopupType
}
