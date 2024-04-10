package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class GetAlbumResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<io.familymoments.app.core.network.dto.response.Album> = emptyList()
)

data class Album(
    val postId: Long = 0,
    val img1: String = "",
)
