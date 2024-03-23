package io.familymoments.app.feature.postdetail.model.response

data class GetPostByIndexResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result:GetPostByIndexResult = GetPostByIndexResult()
)

data class GetPostByIndexResult(
    val postId: Int = 0,
    val writer: String = "",
    val profileImg: String = "",
    val content: String = "",
    val imgs: List<String> = listOf(),
    val createdAt: String = "",
    val countLove: Int = 0,
    val loved: Boolean = false
)
