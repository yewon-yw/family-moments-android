package io.familymoments.app.feature.addpost.model

data class EditPostResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result:EditPostResponseResult = EditPostResponseResult()
)

data class EditPostResponseResult(
    val postId: Long = 0,
    val writer: String = "",
    val profileImg: String = "",
    val content: String = "",
    val imgs: List<String> = listOf(),
    val createdAt: String = "",
    val countLove: Int = 0,
    val loved: Boolean = false
)
