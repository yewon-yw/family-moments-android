package io.familymoments.app.feature.postdetail.model.response

data class GetPostResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result:GetPostResult = GetPostResult()
)

data class GetPostResult(
    val postId: Long = 0,
    val writer: String = "",
    val profileImg: String = "",
    val content: String = "",
    val imgs: List<String> = listOf(),
    val createdAt: String = "",
    val countLove: Int = 0,
    val loved: Boolean = false
)
