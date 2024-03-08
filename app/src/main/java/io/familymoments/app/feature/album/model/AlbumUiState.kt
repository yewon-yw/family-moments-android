package io.familymoments.app.feature.album.model

data class AlbumUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val album: List<Album> = emptyList()
)
