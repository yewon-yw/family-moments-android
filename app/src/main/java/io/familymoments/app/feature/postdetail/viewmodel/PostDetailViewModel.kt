package io.familymoments.app.feature.postdetail.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.HttpResponseMessage.NO_COMMENTS_404
import io.familymoments.app.core.network.HttpResponseMessage.NO_POST_LOVES_404
import io.familymoments.app.core.network.dto.response.GetPostLovesResult
import io.familymoments.app.core.network.dto.response.PostResult
import io.familymoments.app.core.network.repository.CommentRepository
import io.familymoments.app.core.network.repository.PostRepository
import io.familymoments.app.core.util.DateFormatter
import io.familymoments.app.feature.postdetail.uistate.PostDetailPopupType
import io.familymoments.app.feature.postdetail.uistate.PostDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<PostDetailUiState> = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    fun getNickname() {
        viewModelScope.launch {
            val nickname = userInfoPreferencesDataSource.loadUserProfile().nickName
            _uiState.value = _uiState.value.copy(
                userNickname = nickname,
            )
        }
    }

    fun getPostDetail(index: Long) {
        async(
            operation = { postRepository.getPostDetail(index) },
            onSuccess = { response ->
                val localDateTime = DateFormatter.dateTimeToLocalDate(response.result.createdAt)
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        postDetail = response.result.copy(createdAt = localDateTime)
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message
                    )
                }
            })

    }

    fun getComments(index: Long) {
        async(
            operation = { commentRepository.getPostComments(index) },
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        comments = response.result
                    )
                }
            },
            onFailure = { throwable ->
                if (throwable.message == NO_COMMENTS_404) {
                    _uiState.update {
                        it.copy(
                            errorMessage = throwable.message,
                            comments = listOf()
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isSuccess = false,
                            errorMessage = throwable.message
                        )
                    }
                }

            }
        )
    }

    fun getPostLoves(index: Long) {
        async(
            operation = { postRepository.getPostLoves(index) },
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        postLoves = response.result
                    )
                }
            },
            onFailure = { throwable ->
                if (throwable.message == NO_POST_LOVES_404) {
                    _uiState.update {
                        it.copy(
                            errorMessage = throwable.message,
                            postLoves = listOf()
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isSuccess = false,
                            errorMessage = throwable.message
                        )
                    }
                }
            }
        )
    }

    fun postComment(index: Long, content: String) {
        async(
            operation = { commentRepository.postComment(content, index) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        resetComment = true
                    )
                }
                getComments(index)
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message,
                        resetComment = false
                    )
                }
            }
        )
    }

    fun deleteComment(index: Long) {
        async(
            operation = { commentRepository.deleteComment(index) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        popup = PostDetailPopupType.DeleteCommentSuccess,
                    )
                }
                getComments(_uiState.value.postDetail.postId)

            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message,
                        popup = PostDetailPopupType.DeleteCommentFailed
                    )
                }
            }
        )
    }

    fun postCommentLoves(commentId: Long) {
        async(
            operation = { commentRepository.postCommentLoves(commentId) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        comments = it.comments.map { comment ->
                            if (comment.commentId == commentId) {
                                comment.copy(heart = true)
                            } else {
                                comment
                            }
                        }
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message
                    )
                }
            }
        )
    }

    fun deleteCommentLoves(commentId: Long) {
        async(
            operation = { commentRepository.deleteCommentLoves(commentId) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        comments = it.comments.map { comment ->
                            if (comment.commentId == commentId) {
                                comment.copy(heart = false)
                            } else {
                                comment
                            }
                        }
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message
                    )
                }
            }
        )
    }

    fun postPostLoves(index: Long) {
        async(
            operation = { postRepository.postPostLoves(index) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        postDetail = it.postDetail.copy(
                            countLove = it.postDetail.countLove + 1,
                            loved = true
                        )
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message,
                    )
                }
            }
        )
    }

    fun deletePostLoves(index: Long) {
        async(
            operation = { postRepository.deletePostLoves(index) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        postDetail = it.postDetail.copy(
                            countLove = it.postDetail.countLove - 1,
                            loved = false
                        )
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message
                    )
                }
            }
        )
    }

    fun deletePost(index: Long) {
        async(
            operation = { postRepository.deletePost(index) },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        popup = PostDetailPopupType.DeletePostSuccess
                    )
                }
            },
            onFailure = { throwable ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        errorMessage = throwable.message,
                        popup = PostDetailPopupType.DeletePostFailed
                    )
                }
            }
        )
    }

    fun showDeletePostPopup(id: Long) {
        _uiState.update {
            it.copy(popup = PostDetailPopupType.DeletePost(id))
        }
    }

    fun showDeleteCommentPopup(id: Long) {
        _uiState.update {
            it.copy(popup = PostDetailPopupType.DeleteComment(id))
        }
    }

    fun showReportPostPopup(id: Long) {
        _uiState.update {
            it.copy(popup = PostDetailPopupType.ReportPost(id))
        }
    }

    fun showReportCommentPopup(id: Long) {
        _uiState.update {
            it.copy(popup = PostDetailPopupType.ReportComment(id))
        }
    }

    fun showLoveListPopup(loves: List<GetPostLovesResult>) {
        _uiState.update {
            it.copy(popup = PostDetailPopupType.LoveList(loves))
        }
    }

    fun dismissPopup() {
        _uiState.update {
            it.copy(popup = null)
        }
    }

    fun resetSuccess() {
        _uiState.update {
            it.copy(isSuccess = null)
        }
    }

    fun checkPostDetailExist(value: PostResult) = value != PostResult()

    fun makeCommentAvailable() {
        _uiState.update {
            it.copy(resetComment = false)
        }
    }

    fun formatCommentCreatedDate(createdAt: String): String {
        val createdAtWithoutMillie = createdAt.split(".")[0]
        val duration = DateFormatter.dateTimeToDuration(createdAtWithoutMillie)
        return formatDuration(duration)
    }

    private fun formatDuration(durationSeconds: Long): String {
        val oneMinute = 60
        val oneHour = oneMinute * 60
        val oneDay = oneHour * 24
        val oneWeek = oneDay * 7
        val oneMonth = oneDay * 30
        val oneYear = oneDay * 365

        return when {
            durationSeconds < oneMinute -> "방금"
            durationSeconds < oneHour -> "${durationSeconds / oneMinute}분 전"
            durationSeconds < oneDay -> "${durationSeconds / oneHour}시간 전"
            durationSeconds < oneWeek -> "${durationSeconds / oneDay}일 전"
            durationSeconds < oneMonth -> "${durationSeconds / oneWeek}주 전"
            durationSeconds < oneYear -> "${durationSeconds / oneMonth}달 전"
            else -> "${durationSeconds / oneYear}년 전"
        }
    }

    fun reportPost(postId: Long, reason: String, details: String) {
        async(
            operation = {
                postRepository.reportPost(postId, reason, details)
            },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        popup = PostDetailPopupType.ReportPostSuccess
                    )
                }
            },
            onFailure = { e ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        popup = PostDetailPopupType.ReportPostFailed(e.message.toString())
                    )
                }
            }
        )
    }

    fun reportComment(commentId: Long, reason: String, details: String) {
        async(
            operation = {
                commentRepository.reportComment(commentId, reason, details)
            },
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        popup = PostDetailPopupType.ReportCommentSuccess
                    )
                }
            },
            onFailure = { e ->
                _uiState.update {
                    it.copy(
                        isSuccess = false,
                        popup = PostDetailPopupType.ReportCommentFailed(e.message.toString())
                    )
                }
            }
        )
    }
}
