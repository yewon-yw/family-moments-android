package io.familymoments.app.feature.postdetail.uistate

import io.familymoments.app.core.network.dto.response.GetCommentsResult
import io.familymoments.app.core.network.dto.response.GetPostDetailResult
import io.familymoments.app.core.network.dto.response.GetPostLovesResult

data class PostDetailUiState(
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val postDetail:GetPostDetailResult = GetPostDetailResult(),
    val comments:List<GetCommentsResult> = listOf(),
    val postLoves: List<GetPostLovesResult> = listOf(),
    val resetComment:Boolean = false,
    val popup: PostDetailPopupType? = null
)

//data class DetailUiState(
//    val isSuccess: Boolean? = null,
//    val result: GetPostDetailResult = GetPostDetailResult()
//)

//data class CommentsUiState(
//    val isSuccess: Boolean? = null,
//    val result: List<GetCommentsResult> = listOf()
//)

//data class PostLovesUiState(
//    val isSuccess: Boolean? = null,
//    val result: List<GetPostLovesResult> = listOf()
//)

sealed interface PostDetailPopupType {
    data class DeletePost(val postId: Long) : PostDetailPopupType
    data object DeletePostSuccess : PostDetailPopupType
    data object DeletePostFailed : PostDetailPopupType
    data class DeleteComment(val commentId: Long) : PostDetailPopupType
    data object DeleteCommentSuccess : PostDetailPopupType
    data object DeleteCommentFailed : PostDetailPopupType
    data class ReportPost(val postId: Long) : PostDetailPopupType
    data object ReportPostSuccess : PostDetailPopupType
    data object ReportPostFailed : PostDetailPopupType
    data class ReportComment(val commentId: Long) : PostDetailPopupType
    data object ReportCommentSuccess : PostDetailPopupType
    data object ReportCommentFailed : PostDetailPopupType
    data class LoveList(val loves: List<GetPostLovesResult>) : PostDetailPopupType
}
