package io.familymoments.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.ui.component.FMDropDownMenuItem
import io.familymoments.app.ui.component.SelectImageButton
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

@Composable
fun FamilyProfileSetUpScreen() {
    Column {
        CreateFamilyLayoutSkeleton(
            headerBottomPadding = 29.dp,
            header = stringResource(id = R.string.select_create_family_header),
            button = stringResource(id = R.string.next_btn_two_third)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SetUpFamilyName()
                Spacer(modifier = Modifier.height(20.dp))
                SetUpFamilyPicture()
            }
        }

    }

}

@Composable
fun SetUpFamilyName() {
    var familyName by remember {
        mutableStateOf(TextFieldValue())
    }
    Text(
        text = stringResource(R.string.select_family_name),
        style = AppTypography.B1_16,
        color = AppColors.black1
    )
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.grey5)
            .padding(vertical = 12.dp, horizontal = 11.dp),
    ) {
        BasicTextField(value = familyName, onValueChange = { familyName = it }) {
            Text(
                text = stringResource(R.string.family_name_text_field_hint),
                style = AppTypography.LB1_13,
                color = AppColors.grey2
            )
        }
    }

}

@Composable
fun SetUpFamilyPicture() {
    Text(
        text = stringResource(R.string.select_family_image),
        style = AppTypography.B1_16,
        color = AppColors.black1
    )
    Spacer(modifier = Modifier.height(4.dp))
    SelectImageButton(
        modifier = Modifier.height(192.dp),
        isDropDownMenuExist = true,
        menuItems = listOf(FMDropDownMenuItem(stringResource(R.string.drop_down_menu_item_gallery_select), {}), FMDropDownMenuItem(
            stringResource(R.string.drop_down_menu_item_default_image), {}))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFamilyProfileSetUpScreen() {
    FamilyProfileSetUpScreen()
}
