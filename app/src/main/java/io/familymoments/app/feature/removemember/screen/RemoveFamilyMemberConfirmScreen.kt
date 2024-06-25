package io.familymoments.app.feature.removemember.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.removemember.viewmodel.RemoveFamilyMemberConfirmViewModel
import timber.log.Timber
import kotlin.reflect.typeOf

@Composable
fun RemoveFamilyMemberConfirmScreen(
    modifier: Modifier = Modifier,
    viewModel: RemoveFamilyMemberConfirmViewModel,
    navigateBack: () -> Unit,
) {
    RemoveFamilyMemberConfirmScreenUI(
        modifier = modifier,
        userIds = viewModel.userIdsList,
        navigateBack = navigateBack
    )
}

@Composable
fun RemoveFamilyMemberConfirmScreenUI(
    modifier: Modifier = Modifier,
    userIds: List<String> = emptyList(),
    navigateBack: () -> Unit = {}
) {
    val contents = listOf(
        R.string.remove_family_member_confirm_content_1,
        R.string.remove_family_member_confirm_content_2,
        R.string.remove_family_member_confirm_content_3,
        R.string.remove_family_member_confirm_content_4
    )

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.remove_family_member_title),
                style = AppTypography.B1_16,
                color = AppColors.black1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 18.dp)
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
                contents.forEach { resId ->
                    Text(
                        text = stringResource(id = resId),
                        style = AppTypography.B1_16,
                        color = AppColors.deepPurple1,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Text(
                    text = stringResource(id = R.string.remove_family_member_confirm_content_5),
                    style = AppTypography.SH1_20,
                    color = AppColors.deepPurple1,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 31.dp)
                )
            }
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(59.dp),
            onClick = { Timber.d("selected: $userIds") },
            text = stringResource(id = R.string.remove_family_member_confirm_continue_btn),
            containerColor = AppColors.pink1
        )
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 95.dp)
                .height(59.dp),
            onClick = navigateBack,
            text = stringResource(id = R.string.remove_family_member_confirm_cancel_btn),
            containerColor = AppColors.deepPurple1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RemoveFamilyMemberConfirmScreenPreview() {
    RemoveFamilyMemberConfirmScreenUI()
}
