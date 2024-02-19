package io.familymoments.app.feature.profile.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMDropdownMenu
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun ProfileEditScreen(navigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(id = R.string.edit_profile_title),
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_profile_test),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        FMDropdownMenu(
            onGallerySelected = { Log.d("DropdownMenu", "갤러리 선택") },
            onDefaultImageSelected = { Log.d("DropdownMenu", "기본이미지") }
        )
        Spacer(modifier = Modifier.height(34.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            ProfileTextField(stringResource(id = R.string.profile_text_field_name), "홍길동", "이름 입력")
            Spacer(modifier = Modifier.height(42.dp))
            ProfileTextField(stringResource(id = R.string.profile_text_field_nickname), "딸내미", "닉네임 입력")
            Spacer(modifier = Modifier.height(42.dp))
            ProfileTextField(stringResource(id = R.string.profile_text_field_birth_date), "19990909", "생년월일 입력")
            Spacer(modifier = Modifier.height(31.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 11.dp, end = 11.dp),
            ) {
                Button(
                    onClick = { navigateBack() },
                    modifier = Modifier
                        .weight(1f)
                        .size(54.dp),
                    colors = ButtonDefaults.buttonColors(AppColors.purple1, Color.White),
                    shape = RoundedCornerShape(60.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_btn_cancel),
                        style = AppTypography.BTN4_18,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 34.dp))
                Button(
                    onClick = { navigateBack() },
                    modifier = Modifier
                        .weight(1f)
                        .size(54.dp),
                    colors = ButtonDefaults.buttonColors(AppColors.purple2, Color.White),
                    shape = RoundedCornerShape(60.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_btn_done),
                        style = AppTypography.BTN4_18,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileTextField(title: String, value: String, hint: String) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    Text(
        text = title,
        style = AppTypography.B1_16,
        color = AppColors.deepPurple1,
        modifier = Modifier.padding(start = 1.dp, bottom = 2.dp)
    )
    FMTextField(
        value = textFieldValue,
        onValueChange = { newValue -> textFieldValue = newValue },
        hint = hint,
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview() {
    ProfileEditScreen(navigateBack = {})
}
