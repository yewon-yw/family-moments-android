package io.familymoments.app.feature.deletefamily.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.deletefamily.viewmodel.EnterFamilyNameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnterFamilyNameScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: () -> Unit = {},
    viewModel: EnterFamilyNameViewModel,
    familyName: String = ""
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val requester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    var familyNameTextField by remember { mutableStateOf(TextFieldValue()) }

    EnterFamilyNameScreenUI(
        modifier = modifier,
        buttonModifier = Modifier.bringIntoViewRequester(requester),
        navigateBack = navigateBack,
        onValueChanged = { familyNameTextField = it },
        familyNameTextField = familyNameTextField,
        familyName = familyName,
        deleteFamily = viewModel::deleteFamily,
        onFocusChanged = { isFocused ->
            if (isFocused) {
                scope.launch {
                    delay(300)
                    requester.bringIntoView()
                }
            }
        }
    )

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess == true) {
            navigateNext()
        } else if (uiState.isSuccess == false) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun EnterFamilyNameScreenUI(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    onValueChanged: (TextFieldValue) -> Unit = {},
    familyNameTextField: TextFieldValue = TextFieldValue(),
    familyName: String = "",
    deleteFamily: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
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
                .background(AppColors.pink5)
                .onFocusChanged {
                    onFocusChanged(it.isFocused)
                },
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
            modifier = buttonModifier.padding(horizontal = 11.5.dp)
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
                enabled = familyNameTextField.text.trim() == familyName,
                onClick = deleteFamily,
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
