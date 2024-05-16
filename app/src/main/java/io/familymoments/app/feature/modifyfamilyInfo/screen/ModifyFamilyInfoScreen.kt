package io.familymoments.app.feature.modifyfamilyInfo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun ModifyFamilyInfoScreen() {
}

@Composable
fun ModifyFamilyInfoScreenUI() {
    val familyName = remember { mutableStateOf(TextFieldValue("yewon")) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
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
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 15.dp)
                    .size(110.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(id = R.string.modify_family_info_modify_photo),
                style = AppTypography.B1_16,
                color = AppColors.purple2,
                modifier = Modifier.padding(bottom = 18.dp)
            )
            Divider(color = AppColors.grey3, thickness = 0.6.dp)
            Text(
                text = stringResource(id = R.string.modify_family_info_family_name),
                style = AppTypography.B1_16,
                color = AppColors.deepPurple1,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 20.dp, bottom = 9.dp)
            )
            FMTextField(
                onValueChange = { familyName.value = it },
                value = familyName.value,
                hint = "",
                textColor = AppColors.black2
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
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
    ModifyFamilyInfoScreenUI()
}
