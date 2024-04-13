package io.familymoments.app.feature.album.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumPopup(
    isPopupLoading: Boolean?,
    imgs: List<String>,
    onDismissRequest: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imgs.size })

    Dialog(onDismissRequest = onDismissRequest) {
        if (isPopupLoading == false) {
            Column(
                modifier = Modifier
                    .albumShadow()
                    .widthIn(min = 304.dp)
                    .background(color = AppColors.grey6)
                    .padding(top = 9.dp, bottom = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 14.dp, bottom = 8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .clickable(onClick = onDismissRequest)
                            .align(Alignment.CenterEnd),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_album_popup_close),
                        contentDescription = null,
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 17.dp)
                        .height(255.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { index ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            model = imgs[index],
                            contentDescription = null,
                        )
                    }
                    if (imgs.size > 1) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) AppColors.purple2 else AppColors.grey6
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(7.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AlbumPopupPreview() {
    Column(
        modifier = Modifier
            .widthIn(min = 304.dp)
            .background(color = AppColors.grey6)
            .padding(top = 9.dp, bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 14.dp, bottom = 8.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_album_popup_close),
                contentDescription = null,
            )
        }
        Image(
            modifier = Modifier
                .width(270.dp)
                .height(255.dp),
            painter = painterResource(id = R.drawable.img_sample_trees),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
