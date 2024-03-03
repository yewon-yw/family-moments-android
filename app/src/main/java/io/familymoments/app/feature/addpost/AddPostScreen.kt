package io.familymoments.app.feature.addpost

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.convertToBitmap

@Composable
fun AddPostScreen(modifier: Modifier) {
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current

    val bitmapList = remember { mutableStateListOf<Bitmap?>() }
    val launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        bitmapList.add(convertToBitmap(context, it))
    }

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 19.dp),
                    text = "새 글 업로드",
                    style = AppTypography.SH1_20,
                    color = AppColors.deepPurple1,
                    textAlign = TextAlign.Center
                )
            }
            if (bitmapList.isNotEmpty()) {
                items(bitmapList.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(192.dp)
                            .clip(shape = RoundedCornerShape(7.dp))
                            .background(color = AppColors.grey5)
                    ) {
                        Image(
                            bitmap = bitmapList[it]?.asImageBitmap() ?: return@items,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(13.dp))
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 192.dp)
                        .clip(shape = RoundedCornerShape(7.dp))
                        .background(color = AppColors.grey5)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_select_pic),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(top = 3.dp),
                            text = "사진 선택",
                            style = AppTypography.LB1_13,
                            color = AppColors.grey3
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, bottom = 14.dp),
                    text = "글 작성",
                    style = AppTypography.B1_16,
                    color = AppColors.black1,
                    textAlign = TextAlign.Center
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 268.dp)
                        .clip(shape = RoundedCornerShape(7.dp))
                        .background(color = AppColors.grey5)
                        .padding(all = 20.dp),
                    value = content,
                    onValueChange = { content = it },
                    textStyle = AppTypography.LB1_13.copy(color = AppColors.black1),
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) {
                            Text(
                                text = "| 사진과 어울리는 내용을 작성하세요.",
                                style = AppTypography.LB1_13,
                                color = AppColors.grey3
                            )
                        }
                        innerTextField()
                    },
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth()
                .heightIn(min = 59.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(color = AppColors.deepPurple1),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "순간을 가족에게 공유하기",
                style = AppTypography.BTN4_18,
                color = AppColors.grey6,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostScreenPreview() {
    AddPostScreen(modifier = Modifier)
}
