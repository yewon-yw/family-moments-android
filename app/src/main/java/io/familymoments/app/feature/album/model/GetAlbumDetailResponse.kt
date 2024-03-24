package io.familymoments.app.feature.album.model

data class GetAlbumDetailResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: List<String> = emptyList()
)
