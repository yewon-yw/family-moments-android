package io.familymoments.app.feature.modifypassword.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.modifypassword.viewmodel.ModifyPasswordViewModel

@Composable
fun ModifyPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ModifyPasswordViewModel
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        ModifyPasswordTitle()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        ModifyPasswordInfo()
        Spacer(modifier = Modifier.padding(top = 26.dp))
        CurrentPasswordField(viewModel = viewModel)
        NewPasswordField(viewModel = viewModel)
        ModifyPasswordButton(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun ModifyPasswordTitle() {
    Box(
        modifier = Modifier.fillMaxWidth().height(55.dp)
    ) {
        Text(
            text = stringResource(id = R.string.modify_password_title),
            style = AppTypography.B1_16,
            color = AppColors.black1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ModifyPasswordInfo() {
    Text(
        text = stringResource(id = R.string.modify_password_notification_1),
        style = AppTypography.B1_16,
        color = AppColors.deepPurple1,
        modifier = Modifier.padding(bottom = 20.dp)
    )
    Text(
        text = stringResource(id = R.string.modify_password_notification_2),
        style = AppTypography.B1_16,
        color = AppColors.deepPurple1
    )
}

@Composable
private fun CurrentPasswordField(viewModel: ModifyPasswordViewModel) {
    var currentPassword by remember { mutableStateOf(TextFieldValue()) }
    val currentPasswordValid = viewModel.currentPasswordValid.collectAsStateWithLifecycle().value
    val currentPasswordWarning = viewModel.currentPasswordWarning.collectAsStateWithLifecycle().value

    Column {
        FMTextField(
            modifier = Modifier.background(AppColors.pink5).height(46.dp),
            onValueChange = {
                currentPassword = it
                viewModel.checkCurrentPassword(it.text)
            },
            value = currentPassword,
            hint = stringResource(id = R.string.modify_password_current_password),
            borderColor = if (currentPasswordValid || currentPassword.text.isEmpty()) AppColors.grey2 else AppColors.red2,
            showDeleteButton = false,
            showText = false
        )
        ModifyPasswordWarning(
            warningResId = currentPasswordWarning?.stringResId,
            bottomPadding = 70.dp
        )
    }
}

@Composable
private fun NewPasswordField(viewModel: ModifyPasswordViewModel) {
    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var newPasswordCheck by remember { mutableStateOf(TextFieldValue()) }
    val newPasswordWarning = viewModel.newPasswordWarning.collectAsStateWithLifecycle().value

    FMTextField(
        modifier = Modifier.background(AppColors.pink5).height(46.dp),
        onValueChange = {
            newPassword = it
            viewModel.checkNewPassword(it.text, newPasswordCheck.text)
        },
        value = newPassword,
        hint = stringResource(id = R.string.modify_password_new_password),
        borderColor = if (newPasswordWarning == null) AppColors.grey2 else AppColors.red2,
        showDeleteButton = false,
        showText = false
    )
    Spacer(modifier = Modifier.padding(top = 18.dp))
    FMTextField(
        modifier = Modifier.background(AppColors.pink5).height(46.dp),
        onValueChange = {
            newPasswordCheck = it
            viewModel.checkNewPassword(newPassword.text, it.text)
        },
        value = newPasswordCheck,
        hint = stringResource(id = R.string.modify_password_new_password_check),
        borderColor = if (newPasswordWarning == null) AppColors.grey2 else AppColors.red2,
        showDeleteButton = false,
        showText = false
    )
    ModifyPasswordWarning(
        warningResId = newPasswordWarning?.stringResId,
        bottomPadding = 67.dp
    )
}

@Composable
fun ModifyPasswordWarning(
    @StringRes warningResId: Int?,
    bottomPadding: Dp = 0.dp
) {
    if (warningResId == null) {
        Spacer(modifier = Modifier.padding(top = bottomPadding))
    } else {
        Box(
            modifier = Modifier
                .padding(top = 9.dp, bottom = bottomPadding - 25.dp)
        ) {
            Text(
                text = stringResource(id = warningResId),
                style = AppTypography.LB1_13,
                color = AppColors.red2,
                modifier = Modifier.height(16.dp)
            )
        }
    }
}

@Composable
fun ModifyPasswordButton(
    viewModel: ModifyPasswordViewModel
) {
    val currentPasswordValid = viewModel.currentPasswordValid.collectAsStateWithLifecycle().value
    val newPasswordValid = viewModel.newPasswordValid.collectAsStateWithLifecycle().value
    FMButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp),
        onClick = { /* TODO 비밀번호 변경 요청 */ },
        text = stringResource(id = R.string.modify_password_btn),
        enabled = currentPasswordValid && newPasswordValid
    )
}

@Preview(showBackground = true)
@Composable
fun ModifyPasswordScreenPreview() {
    ModifyPasswordScreen(viewModel = ModifyPasswordViewModel())
}
