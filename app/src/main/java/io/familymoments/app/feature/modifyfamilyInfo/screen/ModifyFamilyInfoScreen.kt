package io.familymoments.app.feature.modifyfamilyInfo.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.component.ImageSelectionMenu
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ModifyFamilyInfoScreen() {
    val showDialog = remember { mutableStateOf(false) }
    val familyName = remember { mutableStateOf(TextFieldValue("sweety home")) }

    if (showDialog.value) {
        ImageSelectionMenu(
            onDismissRequest = { showDialog.value = false },
            onGallerySelected = {},
            onDefaultImageSelected = {}
        )
    }

    ModifyFamilyInfoScreenUI(
        onEditImageClicked = { showDialog.value = true },
        onFamilyNameChanged = { familyName.value = it },
        familyName = familyName.value,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifyFamilyInfoScreenUI(
    onEditImageClicked: () -> Unit,
    onFamilyNameChanged: (TextFieldValue) -> Unit,
    familyName: TextFieldValue,
) {
    val requester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = stringResource(id = R.string.modify_family_info_title),
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        Image(
            painter = painterResource(id = R.drawable.img_profile_test),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 15.dp)
                .size(110.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(id = R.string.modify_family_info_edit_image),
            style = AppTypography.B1_16,
            color = AppColors.purple2,
            modifier = Modifier
                .padding(bottom = 18.dp)
                .noRippleClickable {
                    focusManager.clearFocus()
                    onEditImageClicked()
                }
        )
        HorizontalDivider(color = AppColors.grey3, thickness = 0.6.dp)
        Text(
            text = stringResource(id = R.string.modify_family_info_family_name),
            style = AppTypography.B1_16,
            color = AppColors.deepPurple1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 20.dp, bottom = 9.dp)
        )
        FMTextField(
            modifier = Modifier
                .height(46.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        scope.launch {
                            delay(300)
                            requester.bringIntoView()
                        }
                    }
                },
            keyboardActions = KeyboardActions(onDone = {
                // 확인 버튼 클릭 동작 추가
                focusManager.clearFocus()
            }),
            onValueChange = onFamilyNameChanged,
            value = familyName,
            hint = "",
            textColor = AppColors.black2
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewRequester(requester)
                .padding(top = 187.dp)
                .height(59.dp),
            colors = ButtonDefaults.buttonColors(AppColors.deepPurple1)
        ) {
            Text(
                text = stringResource(id = R.string.modify_family_info_btn),
                style = AppTypography.BTN4_18,
                color = AppColors.grey6
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyFamilyInfoScreenPreview() {
    ModifyFamilyInfoScreenUI(onEditImageClicked = {}, onFamilyNameChanged = {}, familyName = TextFieldValue("sweety home"))
}
