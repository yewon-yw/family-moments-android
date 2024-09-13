package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class PostResult(
    val postId: Long = 0,
    val writer: String = "",
    val profileImg: String = "",
    val content: String = "",
    val imgs: List<String> = emptyList(),
    val createdAt: String = "",
    val countLove: Int = 0,
    val loved: Boolean = false,
    val written:Boolean = false
)
