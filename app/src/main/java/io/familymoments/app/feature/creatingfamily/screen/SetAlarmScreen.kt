package io.familymoments.app.feature.creatingfamily.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.LoadingIndicator
import io.familymoments.app.core.component.UploadCycleDropdownMenu
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.choosingfamily.component.ChoosingFamilyHeaderButtonLayout
import io.familymoments.app.feature.creatingfamily.UploadCycle
import io.familymoments.app.feature.creatingfamily.viewmodel.CreatingFamilyViewModel

@Composable
fun SetAlarmScreen(
    viewModel: CreatingFamilyViewModel,
    navigate: (String) -> Unit = {}
) {
    val familyInfo = viewModel.familyProfile.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var uploadCycleTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    var uploadCycle by remember {
        mutableStateOf(UploadCycle.ONE_DAY)
    }
    val createFamilyResultUiState = viewModel.createFamilyResultUiState.collectAsStateWithLifecycle()
    val createFamilySuccessMessage = stringResource(id = R.string.create_family_success_message)

    LaunchedEffect(createFamilyResultUiState.value.isSuccess) {
        if (createFamilyResultUiState.value.isSuccess == true) {
            navigate(createFamilyResultUiState.value.result.inviteCode)
            Toast.makeText(context, createFamilySuccessMessage, Toast.LENGTH_SHORT).show()
        } else if (createFamilyResultUiState.value.isSuccess == false) {
            Toast.makeText(context, createFamilyResultUiState.value.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LoadingIndicator(isLoading = createFamilyResultUiState.value.isLoading ?: false)
    ChoosingFamilyHeaderButtonLayout(
        headerBottomPadding = 34.dp,
        header = stringResource(id = R.string.select_create_family_header),
        button = stringResource(R.string.create_family_btn),
        onClick = {
            viewModel.createFamily(familyInfo.copy(uploadCycle = uploadCycle.number))
        }
    ) {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                UploadCycleDropdownMenu(
                    uploadCycle = uploadCycleTextFieldValue.text,
                    onValueChanged = {
                        uploadCycleTextFieldValue = TextFieldValue(it.value)
                        uploadCycle = it
                    }
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 14.dp),
                text = stringResource(id = R.string.default_alarm_cycle_guide),
                style = AppTypography.LB1_13,
                color = AppColors.grey1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSetAlarmScreen() {
    SetAlarmScreen(hiltViewModel()) {}
}
