package io.familymoments.app.feature.deletefamily.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.deletefamily.viewmodel.DeleteFamilyViewModel

@Composable
fun DeleteFamilyScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    viewModel: DeleteFamilyViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    DeleteFamilyScreenUI(
        modifier = modifier,
        navigateBack = navigateBack,
        navigateNext = {
            if (uiState.familyName.isNotEmpty()) {
                navigateNext(uiState.familyName)
            }
        },
        familyName = uiState.familyName
    )
}

@Composable
fun DeleteFamilyScreenUI(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: () -> Unit = {},
    familyName: String = ""
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
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
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = R.string.delete_family_content_1, familyName),
                    style = AppTypography.B1_16,
                    color = AppColors.grey8,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.delete_family_content_2),
                    style = AppTypography.SH1_20,
                    color = AppColors.red1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 35.dp)
                )
            }
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(59.dp),
            onClick = navigateNext,
            text = stringResource(id = R.string.delete_family_done_btn),
            containerColor = AppColors.pink1
        )
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
                .height(59.dp),
            onClick = navigateBack,
            text = stringResource(id = R.string.delete_family_cancel_btn),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteFamilyScreenPreview() {
    DeleteFamilyScreenUI()
}
