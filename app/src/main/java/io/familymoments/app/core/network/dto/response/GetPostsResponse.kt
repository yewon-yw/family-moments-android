package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class GetPostsResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<Post> = emptyList()
)

@Immutable
data class Post(
    val postId: Long,
    val writer: String,
    val profileImg: String,
    val content: String,
    val imgs: List<String>,
    val createdAt: String,
    val countLove: Int,
    val loved: Boolean
)
