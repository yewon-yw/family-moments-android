package io.familymoments.app.feature.home.model

data class GetPostsResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<Post> = emptyList()
)

data class Post(
    val postId: Int,
    val writer: String,
    val profileImg: String,
    val content: String,
    val imgs: List<String>,
    val createdAt: String,
    val loved: Boolean
)
