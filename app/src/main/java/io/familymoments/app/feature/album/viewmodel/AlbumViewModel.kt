package io.familymoments.app.feature.album.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.album.model.AlbumUiState
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel() {

    private val _albumUiState = MutableStateFlow(AlbumUiState())
    val albumUiState = _albumUiState

    private var minPostId: Long = 0

    fun getAlbum() {
        Timber.d("getAlbum")
        async(
            // TODO: 추후 familyId 수정 예정
            operation = { postRepository.getAlbum(2) },
            onSuccess = {
                Timber.d("getAlbum: onSuccess")
                _albumUiState.value = _albumUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    album = it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                Timber.d("getAlbum: onFailure")
                _albumUiState.value = _albumUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }

    fun loadMoreAlbum() {
        Timber.d("loadMoreAlbum")
        async(
            // TODO: 추후 familyId 수정 예정
            operation = { postRepository.loadMoreAlbum(2, minPostId) },
            onSuccess = {
                Timber.d("loadMoreAlbum: onSuccess")
                _albumUiState.value = _albumUiState.value.copy(
                    isSuccess = true,
                    isLoading = isLoading.value,
                    album = _albumUiState.value.album + it.result
                )
                minPostId = it.result.minOf { post -> post.postId }
            },
            onFailure = {
                Timber.d("loadMoreAlbum: onFailure")
                _albumUiState.value = _albumUiState.value.copy(
                    isSuccess = false,
                    isLoading = isLoading.value,
                    errorMessage = it.message
                )
            }
        )
    }
}
