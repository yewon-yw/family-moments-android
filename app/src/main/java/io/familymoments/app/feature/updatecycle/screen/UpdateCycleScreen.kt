package io.familymoments.app.feature.updatecycle.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.UploadCycleDropdownMenu
import io.familymoments.app.core.component.popup.FamilyPermissionPopup
import io.familymoments.app.core.component.popup.FamilySettingCompletePopup
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.creatingfamily.UploadCycle
import io.familymoments.app.feature.updatecycle.viewmodel.UpdateCycleViewModel

@Composable
fun UpdateCycleScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    viewModel: UpdateCycleViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    var showPermissionPopup by remember { mutableStateOf(false) }
    var showCompletePopup by remember { mutableStateOf(false) }
    var uploadCycle by remember { mutableStateOf(UploadCycle.ONE_DAY) }

    LaunchedEffect(uiState.uploadCycle) { uploadCycle = UploadCycle.fromNumber(uiState.uploadCycle) }
    LaunchedEffect(uiState.isOwner) { showPermissionPopup = !uiState.isOwner }
    LaunchedEffect(uiState.isSuccess) { showCompletePopup = uiState.isSuccess }

    FamilyPermissionPopup(showPermissionPopup, navigateBack) { showPermissionPopup = false }
    FamilySettingCompletePopup(showCompletePopup, navigateBack) { showCompletePopup = false }

    UpdateCycleScreenUI(
        modifier = modifier,
        uploadCycle = uploadCycle,
        onItemClicked = { uploadCycle = it },
        updateCycle = viewModel::updateCycle
    )
}

@Composable
fun UpdateCycleScreenUI(
    modifier: Modifier = Modifier,
    uploadCycle: UploadCycle = UploadCycle.ONE_DAY,
    onItemClicked: (UploadCycle) -> Unit = {},
    updateCycle: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.family_setting_title),
            style = AppTypography.B1_16,
            color = AppColors.black1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.update_cycle_title),
            style = AppTypography.SH2_18,
            color = AppColors.grey8,
            modifier = Modifier.padding(start = 2.dp, top = 1.dp, bottom = 33.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            UploadCycleDropdownMenu(
                uploadCycle = uploadCycle.value,
                onValueChanged = onItemClicked
            )
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
                .height(59.dp),
            onClick = { updateCycle(uploadCycle.number ?: 0) },
            text = stringResource(id = R.string.update_cycle_btn)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateCycleScreenPreview() {
    UpdateCycleScreenUI()
}
