package io.familymoments.app.feature.postdetail.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.core.util.noRippleClickable
import io.familymoments.app.feature.home.component.postItemContentShadow

@Composable
fun PostDetailScreen(
    modifier: Modifier
) {
    LazyColumn {
        item {
            Column(modifier = modifier.padding(top = 26.dp, bottom = 63.dp)) {
                WriterInfo()
                Spacer(modifier = Modifier.height(15.dp))
                Divider(Modifier.height(1.dp), color = AppColors.deepPurple3)
                Spacer(modifier = Modifier.height(19.dp))
                PostBox()
            }
        }

    }

}

@Composable
fun WriterInfo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(11.dp))
        Image(
            painter = painterResource(id = R.drawable.default_profile),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = "딸내미", style = AppTypography.B1_16, color = AppColors.black2, modifier = Modifier.weight(1f))
        Text(text = "2023.06.12 (토)", style = AppTypography.LB1_13, color = AppColors.grey3)
    }
}

@Composable
fun PostBox() {
    Box(modifier = Modifier.postItemContentShadow()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 11.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(AppColors.grey6)
        ) {
            Post()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(color = AppColors.grey4)
            )
            Comments()
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}

@Composable
fun Post() {
    Column {
        PostPhotos()
        PostContent()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostPhotos() {
    Box(
        modifier = Modifier
            .height(168.dp)
    ) {
        val pagerState = rememberPagerState(pageCount = { 3 })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .height(180.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.img_sample_trees),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
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

@Composable
fun PostContent() {
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
                text = "우리 가족사진 삼각대로 찍기~~\n너무 잘나왔다!",
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
                    PostDetailDropdownMenu(
                        items = listOf(
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_modify), {}),
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_report), {}),
                            Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_delete), {}),
                        ),
                        expanded = expanded
                    ) { expanded = it }
                }

                Spacer(modifier = Modifier.height(6.dp))

                var commentLike by remember {
                    mutableStateOf(false)
                }

                Icon(
                    imageVector =
                    if (!commentLike) ImageVector.vectorResource(R.drawable.ic_heart_empty)
                    else ImageVector.vectorResource(R.drawable.ic_heart_filled),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable { commentLike = !commentLike }
                )
                Spacer(modifier = Modifier.height(3.dp))
                androidx.compose.material3.Text(text = "3", style = AppTypography.LB2_11, color = AppColors.black2)
            }
        }
    }
}

@Composable
fun Comments() {
    Column {
        Row(
            modifier = Modifier.padding(
                top = 14.dp,
                start = 16.dp,
                end = 12.dp
            )
        ) {
            Text(
                text = "댓글 2개",
                style = AppTypography.B2_14,
                color = AppColors.grey2,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.post_detail_screen_show_heart),
                style = AppTypography.B2_14,
                color = AppColors.grey2
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CommentTextField()
        Spacer(modifier = Modifier.height(18.dp))
        CommentItems()
    }
}

@Composable
fun CommentTextField() {
    var comments by remember {
        mutableStateOf(TextFieldValue())
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 10.dp, end = 12.dp)
            .background(
                color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
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
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(end = 6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .height(52.dp)
                    .width(42.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.purple1),
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

@Composable
fun CommentItems() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CommentItem()
        CommentItem()
        CommentItem()
        CommentItem()
    }

}

@Composable
fun CommentItem() {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 12.dp)
            .background(
                color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(modifier = Modifier.padding(start = 11.dp, top = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.default_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 11.dp)
                        .clip(CircleShape)
                        .size(24.dp)
                )
                Text(
                    text = "마미",
                    style = AppTypography.BTN6_13,
                    color = AppColors.black2
                )
            }
            Text(
                modifier = Modifier.padding(start = 11.dp, bottom = 14.58.dp, top = 9.73.dp),
                text = "우리 가족 사진 너무 잘 나왔다~~!\n" +
                    "엄마도 카톡으로 보내줘~",
                style = AppTypography.LB2_11,
                color = AppColors.black2
            )
        }

        Column(
            modifier = Modifier
                .padding(end = 6.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_three_dots_row),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .noRippleClickable {
                            expanded = true
                        },
                    tint = Color.Unspecified,
                )
                PostDetailDropdownMenu(
                    items = listOf(
                        Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_report), {}),
                        Pair(stringResource(id = R.string.post_detail_screen_drop_down_menu_delete), {}),
                    ),
                    expanded = expanded
                ) { expanded = it }
            }

            var commentLike by remember {
                mutableStateOf(false)
            }

            Icon(
                imageVector =
                if (!commentLike) ImageVector.vectorResource(R.drawable.ic_heart_empty)
                else ImageVector.vectorResource(R.drawable.ic_heart_filled),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End)
                    .noRippleClickable { commentLike = !commentLike }
            )
            Text(
                text = "7분 전",
                style = AppTypography.LB2_11,
                color = AppColors.grey3,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }
}

@Composable
fun PostDetailDropdownMenu(
    items: List<Pair<String, () -> Unit>>,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
        modifier = modifier.then(
            Modifier
                .width(112.dp)
                .background(color = AppColors.deepPurple3, shape = RoundedCornerShape(5.dp))
        )
    )
    {
        items.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it.first,
                        style = AppTypography.LB2_11,
                        color = AppColors.grey6
                    )
                },
                onClick = {
                    onExpandedChange(false)
                    it.second()
                },
                modifier = Modifier.height(35.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailPreview() {
    FamilyMomentsTheme {
        PostDetailScreen(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailDropdownMenuPreview() {
    FamilyMomentsTheme {
        PostDetailDropdownMenu(items = listOf(), modifier = Modifier, true) {}
    }
}
