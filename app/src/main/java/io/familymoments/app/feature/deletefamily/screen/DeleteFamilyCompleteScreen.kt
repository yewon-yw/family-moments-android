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
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun DeleteFamilyCompleteScreen(
    modifier: Modifier = Modifier
) {
    DeleteFamilyCompleteScreenUI(modifier = modifier)
}

@Composable
fun DeleteFamilyCompleteScreenUI(
    modifier: Modifier = Modifier
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
                    text = stringResource(id = R.string.delete_family_complete_content_1),
                    style = AppTypography.BTN4_18,
                    color = AppColors.grey8,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.delete_family_complete_content_2),
                    style = AppTypography.BTN3_20,
                    color = AppColors.grey8,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 79.dp)
                )
            }
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(59.dp),
            onClick = { },
            text = stringResource(id = R.string.delete_family_complete_done_btn),
            containerColor = AppColors.pink1
        )
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
                .height(59.dp),
            onClick = { },
            text = stringResource(id = R.string.delete_family_complete_cancel_btn),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteFamilyCompleteScreenPreview() {
    DeleteFamilyCompleteScreenUI()
}
