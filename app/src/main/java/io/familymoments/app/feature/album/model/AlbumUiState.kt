package io.familymoments.app.feature.album.model

import io.familymoments.app.core.network.HttpResponseMessage.NO_POST_404

data class AlbumUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val album: List<Album> = emptyList(),
    val albumDetail: AlbumDetailUiState = AlbumDetailUiState()
) {
    val hasNoPost = isSuccess == false && isLoading == false && errorMessage == NO_POST_404 && album.isEmpty()
}

data class AlbumDetailUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val imgs: List<String> = emptyList()
)
