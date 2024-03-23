package io.familymoments.app.feature.postdetail.model.response

data class GetCommentsByPostIndexResponse(
    val isSuccess: Boolean = false,
    val code: Int = -1,
    val message: String = "",
    val result: List<GetCommentsByPostIndexResult> = listOf()
)

data class GetCommentsByPostIndexResult(
    val commentId: Int = -1,
    val nickname: String = "",
    val profileImg: String = "",
    val content: String = "",
    val heart: Boolean = false,
    val createdAt: String = ""
)
