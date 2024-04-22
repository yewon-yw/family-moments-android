package io.familymoments.app.feature.postdetail.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.component.PostDropdownMenu
import io.familymoments.app.core.component.popup.CompletePopUp
import io.familymoments.app.core.component.popup.DeletePopUp
import io.familymoments.app.core.component.popup.LoveListPopUp
import io.familymoments.app.core.component.popup.ReportPopUp
import io.familymoments.app.core.network.dto.response.GetCommentsResult
import io.familymoments.app.core.network.dto.response.GetPostDetailResult
import io.familymoments.app.core.network.dto.response.GetPostLovesResult
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.noRippleClickable
import io.familymoments.app.feature.postdetail.component.postDetailContentShadow
import io.familymoments.app.feature.postdetail.uistate.PostDetailPopupType
import io.familymoments.app.feature.postdetail.uistate.PostDetailUiState
import io.familymoments.app.feature.postdetail.viewmodel.PostDetailViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel,
    index: Long,
    modifier: Modifier,
    navigateToBack: () -> Unit,
    navigateToModify: (GetPostDetailResult) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getPostDetail(index)
        viewModel.getComments(index)
        viewModel.getPostLoves(index)
    }
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val popup = uiState.popup
    val postDetail = uiState.postDetail
    val comments = uiState.comments
    val postLoves = uiState.postLoves

    LaunchedEffectShowPopup(
        popup,
        viewModel::deletePost,
        viewModel::deleteComment,
        viewModel::dismissPopup,
        viewModel::getComments,
        navigateToBack,
        postDetail.postId
    )
    LaunchedEffectShowErrorMessage(uiState, context, viewModel::resetSuccess)

    val pagerState = rememberPagerState(pageCount = { postDetail.imgs.size })

    LazyColumn {
        item {
            Column(modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 26.dp, bottom = 63.dp)) {
                if (viewModel.checkPostDetailExist(postDetail)) {
                    WriterInfo(
                        writer = postDetail.writer,
                        profileImg = postDetail.profileImg,
                        createdAt = viewModel.formatPostCreatedDate(postDetail.createdAt),
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(Modifier.height(1.dp), color = AppColors.deepPurple3)
                Spacer(modifier = Modifier.height(19.dp))
                Box(modifier = Modifier.postDetailContentShadow()) {
                    Column(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(AppColors.grey6)
                    ) {
                        PostPhotos(postDetail.imgs, pagerState)
                        PostContent(
                            postDetail,
                            viewModel::showDeletePostPopup,
                            viewModel::showReportPostPopup,
                            viewModel::deletePostLoves,
                            viewModel::postPostLoves,
                        ) { navigateToModify(postDetail) }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .background(color = AppColors.grey4)
                        )
                        CommentTextField(
                            uiState.isSuccess,
                            comments.size,
                            postDetail.postId,
                            viewModel::postComment,
                            viewModel::showLoveListPopup,
                            postLoves,
                            viewModel::getComments,
                            viewModel::resetSuccess
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        if (comments.isNotEmpty()) {
                            CommentItems(
                                comments,
                                viewModel::formatCommentCreatedDate,
                                viewModel::showReportCommentPopup,
                                viewModel::showDeleteCommentPopup,
                                viewModel::deleteCommentLoves,
                                viewModel::postCommentLoves
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
    }

}

@Composable
fun LaunchedEffectShowPopup(
    popup: PostDetailPopupType?,
    deletePost: (Long) -> Unit,
    deleteComment: (Long) -> Unit,
    dismissPopup: () -> Unit,
    getComments: (Long) -> Unit,
    navigateToBack: () -> Unit,
    postId: Long
) {
    val showPopup = remember { mutableStateOf(false) }
    LaunchedEffect(popup) {
        showPopup.value = popup != null
    }
    if (showPopup.value) {
        when (popup) {
            is PostDetailPopupType.DeleteComment -> {
                DeletePopUp(
                    content = stringResource(id = R.string.post_detail_pop_up_delete_comment_label),
                    delete = { deleteComment(popup.commentId) },
                    onDismissRequest = dismissPopup
                )
            }

            PostDetailPopupType.DeleteCommentFailed -> {
                //todo: 댓글 삭제 실패 팝업
            }

            PostDetailPopupType.DeleteCommentSuccess -> {
                CompletePopUp(
                    content = stringResource(id = R.string.post_detail_delete_complete_pop_label),
                    onDismissRequest = {
                        dismissPopup()
                        getComments(postId)
                    }
                )
            }

            is PostDetailPopupType.DeletePost -> {
                DeletePopUp(
                    content = stringResource(id = R.string.post_detail_delete_post_pop_up_label),
                    delete = { deletePost(popup.postId) },
                    onDismissRequest = dismissPopup
                )
            }

            PostDetailPopupType.DeletePostFailed -> {
                //todo: 글 삭제 실패 팝업
            }

            PostDetailPopupType.DeletePostSuccess -> {
                CompletePopUp(
                    content = stringResource(id = R.string.post_detail_delete_complete_pop_label),
                    onDismissRequest = {
                        dismissPopup()
                        navigateToBack()
                    }
                )
            }

            is PostDetailPopupType.ReportComment -> {
                ReportPopUp(
                    onDismissRequest = dismissPopup,
                    onReportRequest = {
                        //todo: 댓글 신고하기 기능 구현
                    }
                )
            }

            PostDetailPopupType.ReportCommentFailed -> {
                //todo: 댓글 신고 실패 팝업
            }

            PostDetailPopupType.ReportCommentSuccess -> {
                //todo: 댓글 신고 성공 팝업
            }

            is PostDetailPopupType.ReportPost -> {
                ReportPopUp(
                    onDismissRequest = dismissPopup,
                    onReportRequest = {
                        //todo: 글 신고하기 기능 구현
                    }
                )
            }

            PostDetailPopupType.ReportPostFailed -> {
                //todo: 글 신고 실패 팝업
            }

            PostDetailPopupType.ReportPostSuccess -> {
                //todo: 글 신고 성공 팝업
            }

            is PostDetailPopupType.LoveList -> {
                LoveListPopUp(
                    postLoves = popup.loves,
                    onDismissRequest = dismissPopup
                )
            }

            null -> {}
        }
    }
}

@Composable
fun LaunchedEffectShowErrorMessage(
    uiState: PostDetailUiState,
    context: Context,
    resetSuccess: () -> Unit
) {
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess == false) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            resetSuccess()
        }
    }
}

@Composable
fun WriterInfo(
    writer: String,
    profileImg: String,
    createdAt: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(11.dp))
        AsyncImage(
            model = profileImg,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = writer,
            style = AppTypography.B1_16,
            color = AppColors.black2,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = createdAt,
            style = AppTypography.LB1_13,
            color = AppColors.grey3
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostPhotos(imgs: List<String>, pagerState: PagerState) {
    Box(
        modifier = Modifier
            .height(168.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                model = imgs[page],
                contentDescription = null
            )
        }
        if (imgs.size > 1) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) AppColors.purple2 else AppColors.grey6
                    Box(
                        modifier = Modifier
                            .padding(horizontal = (3.5).dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(7.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PostContent(
    postInfo: GetPostDetailResult,
    showDeletePostPopup: (Long) -> Unit,
    showReportPostPopup: (Long) -> Unit,
    deletePostLoves: (Long) -> Unit,
    postPostLoves: (Long) -> Unit,
    navigateToModify: () -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .heightIn(183.dp)
                    .padding(top = 11.dp, end = 10.dp)
                    .weight(1f),
                text = postInfo.content,
                style = AppTypography.B2_14,
                color = AppColors.black2
            )
            Column(
                modifier = Modifier.padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_three_dots_row),
                        tint = AppColors.deepPurple1,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            expanded = true
                        }
                    )

                    PostDropdownMenu(
                        items = listOf(
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_modify)) {
                                navigateToModify()
                            },
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_report)) {
                                showReportPostPopup(postInfo.postId)
                            },
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_delete)) {
                                showDeletePostPopup(postInfo.postId)
                            },
                        ),
                        expanded = expanded
                    ) { expanded = it }
                }

                Spacer(modifier = Modifier.height(6.dp))

                var lovedState by remember {
                    mutableStateOf(postInfo.loved)
                }
                var countLoveState by remember {
                    mutableIntStateOf(postInfo.countLove)
                }

                Icon(
                    imageVector =
                    if (!lovedState) ImageVector.vectorResource(R.drawable.ic_heart_empty)
                    else ImageVector.vectorResource(R.drawable.ic_heart_filled),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable {
                        if (lovedState) {
                            deletePostLoves(postInfo.postId)
                            countLoveState -= 1
                        } else {
                            postPostLoves(postInfo.postId)
                            countLoveState += 1
                        }
                        lovedState = !lovedState
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = countLoveState.toString(),
                    style = AppTypography.LB2_11,
                    color = AppColors.black2
                )
            }
        }
    }
}

@Composable
fun CommentTextField(
    postCommentsSuccess: Boolean?,
    commentsCount: Int,
    postId: Long,
    postComment: (Long, String) -> Unit,
    showLoveListPopup: (List<GetPostLovesResult>) -> Unit,
    postLoves: List<GetPostLovesResult>,
    getComments: (Long) -> Unit,
    resetSuccess: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(
                top = 14.dp,
                start = 16.dp,
                end = 12.dp
            )
        ) {
            Text(
                text = "댓글 ${commentsCount}개",
                style = AppTypography.B2_14,
                color = AppColors.grey2,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.post_detail_screen_show_heart),
                style = AppTypography.B2_14,
                color = AppColors.grey2,
                modifier = Modifier.noRippleClickable {
                    showLoveListPopup(postLoves)
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        var comments by remember {
            mutableStateOf(TextFieldValue())
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = 10.dp, end = 12.dp)
                .background(
                    color = AppColors.grey4,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        ) {
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

            Row(modifier = Modifier.fillMaxHeight()) {
                BasicTextField(
                    value = comments,
                    onValueChange = {
                        if (it.text.length <= 50) comments = it
                    },
                    textStyle = AppTypography.LB2_11.copy(AppColors.black1),
                    modifier = Modifier
                        .weight(1f)
                        .padding(11.dp)
                        .focusRequester(focusRequester)
                ) { innerTextField ->
                    if (comments.text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.post_detail_screen_comment_text_field_hint),
                            color = AppColors.grey3,
                            style = AppTypography.LB2_11
                        )
                    }
                    innerTextField()
                }

                if (postCommentsSuccess == true) {
                    comments = TextFieldValue()
                    focusManager.clearFocus()
                    getComments(postId)
                    resetSuccess()
                }
                Button(
                    onClick = {
                        postComment(postId, comments.text)
                    },
                    enabled = comments.text.trim().isNotEmpty(),
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .height(52.dp)
                        .width(42.dp)
                        .align(Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.purple1),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = stringResource(R.string.post_detail_screen_comment_post_button),
                        style = AppTypography.BTN6_13,
                        color = AppColors.grey6
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItems(
    comments: List<GetCommentsResult>,
    formatCommentCreatedDate: (String) -> String,
    showReportCommentPopup: (Long) -> Unit,
    showDeleteCommentPopup: (Long) -> Unit,
    deleteCommentLoves: (Long) -> Unit,
    postCommentLoves: (Long) -> Unit,

    ) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        comments.forEach {
            CommentItem(
                it,
                formatCommentCreatedDate,
                showReportCommentPopup,
                showDeleteCommentPopup,
                deleteCommentLoves,
                postCommentLoves
            )
        }
    }

}

@Composable
fun CommentItem(
    comment: GetCommentsResult,
    formatCommentCreatedDate: (String) -> String,
    showReportCommentPopup: (Long) -> Unit,
    showDeleteCommentPopup: (Long) -> Unit,
    deleteCommentLoves: (Long) -> Unit,
    postCommentLoves: (Long) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 12.dp)
            .background(
                color = AppColors.grey4,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(modifier = Modifier.padding(start = 11.dp, top = 10.dp)) {
                AsyncImage(
                    model = comment.profileImg,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 11.dp)
                        .clip(CircleShape)
                        .size(24.dp)
                )
                Text(
                    text = comment.nickname,
                    style = AppTypography.BTN6_13,
                    color = AppColors.black2
                )
            }
            Text(
                modifier = Modifier.padding(start = 11.dp, bottom = 14.58.dp, top = 9.73.dp),
                text = comment.content,
                style = AppTypography.LB2_11,
                color = AppColors.black2
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 3.dp, end = 6.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var commentLikeState by remember {
                mutableStateOf(comment.heart)
            }
            Box(modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_three_dots_row),
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable { expanded = true },
                    tint = Color.Unspecified,
                )
                PostDropdownMenu(
                    items = listOf(
                        Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_report)) {
                            showReportCommentPopup(comment.commentId)
                        },
                        Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_delete)) {
                            showDeleteCommentPopup(comment.commentId)
                        },
                    ),
                    expanded = expanded
                ) { expanded = it }
            }

            Icon(
                imageVector =
                if (!commentLikeState) ImageVector.vectorResource(R.drawable.ic_heart_empty)
                else ImageVector.vectorResource(R.drawable.ic_heart_filled),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End)
                    .noRippleClickable {
                        if (commentLikeState) {
                            deleteCommentLoves(comment.commentId)
                        } else {
                            postCommentLoves(comment.commentId)
                        }
                        commentLikeState = !commentLikeState
                    }
            )
            Text(
                text = formatCommentCreatedDate(comment.createdAt),
                style = AppTypography.LB2_11,
                color = AppColors.grey3,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailPreview() {
    PostDetailScreen(hiltViewModel(), 0, modifier = Modifier, {}) {}
}

@Preview(showBackground = true)
@Composable
fun PostDetailDropdownMenuPreview() {
    PostDropdownMenu(items = listOf(), modifier = Modifier, true) {}
}
