package io.familymoments.app.feature.leavefamily.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun LeaveFamilyScreen(
    modifier: Modifier = Modifier
) {
    LeaveFamilyScreenUI(
        modifier = modifier
    )
}

@Composable
fun LeaveFamilyScreenUI(
    modifier: Modifier = Modifier
) {
    val contents = listOf(
        R.string.leave_family_content_1,
        R.string.leave_family_content_2,
        R.string.leave_family_content_3,
        R.string.leave_family_content_4
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.leave_family_title),
                style = AppTypography.B1_16,
                color = AppColors.black1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 18.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                contents.forEach { resId ->
                    Text(
                        text = stringResource(id = resId),
                        style = AppTypography.B1_16,
                        color = AppColors.deepPurple1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Text(
                    text = stringResource(id = R.string.leave_family_content_5),
                    style = AppTypography.SH1_20,
                    color = AppColors.deepPurple1,
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(59.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.pink1, contentColor = AppColors.grey6)
        ) {
            Text(
                text = stringResource(id = R.string.leave_family_done_btn),
                style = AppTypography.BTN4_18
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
                .height(59.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.deepPurple1, contentColor = AppColors.grey6)
        ) {
            Text(
                text = stringResource(id = R.string.leave_family_cancel_btn),
                style = AppTypography.BTN4_18
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaveFamilyScreenPreview() {
    LeaveFamilyScreenUI()
}
