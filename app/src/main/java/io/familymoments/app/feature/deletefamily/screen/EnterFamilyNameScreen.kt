package io.familymoments.app.feature.deletefamily.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun EnterFamilyNameScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: () -> Unit = {}
) {
    var familyNameTextField by remember { mutableStateOf(TextFieldValue()) }
    EnterFamilyNameScreenUI(
        modifier = modifier,
        navigateBack = navigateBack,
        navigateNext = navigateNext,
        onValueChanged = { familyNameTextField = it },
        familyNameTextField = familyNameTextField,
    )
}

@Composable
fun EnterFamilyNameScreenUI(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: () -> Unit = {},
    onValueChanged: (TextFieldValue) -> Unit = {},
    familyNameTextField: TextFieldValue = TextFieldValue(),
    familyName: String = "sweety home"
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp)
        ) {
            Text(
                text = stringResource(id = R.string.delete_family_title),
                style = AppTypography.B1_16,
                color = AppColors.black1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = stringResource(id = R.string.delete_family_enter_family_name_content),
            style = AppTypography.BTN4_18,
            color = AppColors.grey8,
            modifier = Modifier.padding(top = 100.dp)
        )
        FMTextField(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 9.dp)
                .height(46.dp)
                .background(AppColors.pink5),
            onValueChange = onValueChanged,
            value = familyNameTextField
        )
        Text(
            text = stringResource(id = R.string.delete_family_enter_family_name, familyName),
            style = AppTypography.SH2_18,
            color = AppColors.grey3,
            modifier = Modifier.padding(bottom = 210.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 11.5.dp)
        ) {
            FMButton(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                onClick = navigateBack,
                text = stringResource(id = R.string.delete_family_enter_family_name_cancel_btn)
            )
            Spacer(modifier = Modifier.width(34.dp))
            FMButton(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                enabled = familyNameTextField.text == familyName,
                onClick = {
                    // TODO 가족 삭제 진행
                    navigateNext()
                },
                containerColor = AppColors.pink1,
                text = stringResource(id = R.string.delete_family_enter_family_name_done_btn)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterFamilyNameScreenPreview() {
    EnterFamilyNameScreenUI()
}
