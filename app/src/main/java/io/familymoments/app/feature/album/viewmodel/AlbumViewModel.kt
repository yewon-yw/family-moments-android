package io.familymoments.app.feature.album.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.feature.album.uistate.AlbumDetailUiState
import io.familymoments.app.feature.album.uistate.AlbumUiState
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : BaseViewModel() {

    private val _albumUiState = MutableStateFlow(AlbumUiState())
    val albumUiState = _albumUiState

    private var minPostId: Long = 0

    fun getAlbum() {
        Timber.d("getAlbum")
        async(
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.getAlbum(familyId)
            },
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
            operation = {
                val familyId = userInfoPreferencesDataSource.loadFamilyId()
                postRepository.loadMoreAlbum(familyId, minPostId)
            },
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

    fun getAlbumDetail(postId: Long) {
        Timber.d("getAlbumDetail")
        async(
            operation = { postRepository.getAlbumDetail(postId) },
            onSuccess = {
                Timber.d("getAlbumDetail: onSuccess")
                _albumUiState.value = _albumUiState.value.copy(
                    albumDetail = AlbumDetailUiState(
                        isSuccess = true,
                        isLoading = isLoading.value,
                        imgs = it.result
                    )
                )
            },
            onFailure = {
                Timber.d("getAlbumDetail: onFailure")
                _albumUiState.value = _albumUiState.value.copy(
                    albumDetail = AlbumDetailUiState(
                        isSuccess = false,
                        isLoading = isLoading.value,
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    fun setAlbumDetailEmpty() {
        _albumUiState.value = _albumUiState.value.copy(
            albumDetail = AlbumDetailUiState()
        )
    }
}
