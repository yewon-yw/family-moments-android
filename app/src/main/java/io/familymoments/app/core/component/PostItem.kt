package io.familymoments.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.network.dto.response.PostResult
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.formattedPostDate
import io.familymoments.app.core.util.noRippleClickable
import io.familymoments.app.feature.home.component.postItemContentShadow

@Composable
fun PostItem(
    post: PostResult,
    navigateToPostDetail: (Int) -> Unit,
    navigateToEditPost: (PostResult) -> Unit,
    onClickPostLoves: () -> Unit,
    showDeletePostPopup: () -> Unit,
    showReportPostPopup: () -> Unit
) {
    val menuExpanded = remember { mutableStateOf(false) }
    val isUserPost = post.written
    val pagerState = rememberPagerState(pageCount = { post.imgs.size })
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        PostItemHeader(post = post)
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.postItemContentShadow()) {
            PostItemContent(
                post = post,
                navigateToPostDetail = navigateToPostDetail,
                navigateToEditPost = navigateToEditPost,
                onClickPostLoves = onClickPostLoves,
                showDeletePostPopup = showDeletePostPopup,
                showReportPostPopup = showReportPostPopup,
                pagerState = pagerState,
                menuExpanded = menuExpanded.value,
                onMenuExpandedChanged = { menuExpanded.value = it },
                isUserPost = isUserPost
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PostItemHeader(post: PostResult) {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(39.dp)
                .clip(shape = CircleShape),
            model = post.profileImg,
            contentScale = ContentScale.Crop,
            contentDescription = "profileImg"
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = post.writer,
            style = AppTypography.LB1_13,
            color = AppColors.black2
        )
        Text(
            text = post.createdAt.formattedPostDate(),
            style = AppTypography.LB2_11,
            color = AppColors.grey3
        )
    }
}

@Composable
private fun PostItemContent(
    post: PostResult,
    navigateToPostDetail: (Int) -> Unit,
    navigateToEditPost: (PostResult) -> Unit,
    onClickPostLoves: () -> Unit,
    showDeletePostPopup: () -> Unit,
    showReportPostPopup: () -> Unit,
    pagerState: PagerState,
    menuExpanded: Boolean = false,
    onMenuExpandedChanged: (Boolean) -> Unit,
    isUserPost: Boolean = false
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 11.dp)
            .height(282.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(AppColors.grey6)
            .clickable {
                navigateToPostDetail(post.postId.toInt())
            }
    ) {
        PostPhotos(post = post, pagerState = pagerState)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 6.dp, top = 11.dp, bottom = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .height(114.dp)
                    .padding(end = 10.dp)
                    .weight(1f),
                text = post.content,
                style = AppTypography.B2_14,
                color = AppColors.black2,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                modifier = Modifier.padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_three_dots_row),
                        tint = AppColors.grey8,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            onMenuExpandedChanged(true)
                        }
                    )
                    if (menuExpanded) {
                        PostDropdownMenu(
                            items =
                            if (isUserPost) listOf(
                                Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_modify)) {
                                    navigateToEditPost(post)
                                },
                                Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_delete)) {
                                    showDeletePostPopup()
                                    onMenuExpandedChanged(false)
                                },
                            ) else listOf(
                                Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_report)) {
                                    showReportPostPopup()
                                    onMenuExpandedChanged(false)
                                },
                            ),
                            expanded = true,
                            onDismissRequest = {
                                onMenuExpandedChanged(false)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (post.loved) R.drawable.ic_heart_filled
                        else R.drawable.ic_heart_empty
                    ),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable { onClickPostLoves() }
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = post.countLove.toString(), style = AppTypography.LB2_11, color = AppColors.black1)
            }
        }
    }
}

@Composable
fun PostPhotos(
    post: PostResult,
    pagerState: PagerState
) {
    Box(
        modifier = Modifier
            .height(168.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                model = post.imgs[index],
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
        if (post.imgs.size > 1) {
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

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    PostItem(
        post = PostResult(
            postId = 0,
            writer = "test",
            profileImg = "",
            createdAt = "2023-03-12",
            content = "test",
            imgs = listOf(""),
            countLove = 0,
            loved = false,
            written = true
        ),
        navigateToPostDetail = {},
        navigateToEditPost = {},
        onClickPostLoves = {},
        showDeletePostPopup = {},
        showReportPostPopup = {}
    )
}
