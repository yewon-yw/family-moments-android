package io.familymoments.app.feature.album.model

data class GetAlbumResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<Album> = emptyList()
)

data class Album(
    val postId: Long = 0,
    val img1: String = "",
)
