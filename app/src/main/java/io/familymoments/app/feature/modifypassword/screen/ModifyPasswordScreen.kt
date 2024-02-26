package io.familymoments.app.feature.modifypassword.screen

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
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun ModifyPasswordScreen(
    modifier: Modifier = Modifier
) {
    var currentPwd by remember { mutableStateOf(TextFieldValue()) }
    var newPwd by remember { mutableStateOf(TextFieldValue()) }
    var newPwdCheck by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        ModifyPasswordTitle()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        ModifyPasswordInfo()
        Spacer(modifier = Modifier.padding(top = 26.dp))
        FMTextField(
            modifier = Modifier.background(AppColors.pink5).height(46.dp),
            onValueChange = { currentPwd = it },
            value = currentPwd,
            hint = stringResource(id = R.string.modify_password_current_password)
        )
        Spacer(modifier = Modifier.padding(top = 70.dp))
        FMTextField(
            modifier = Modifier.background(AppColors.pink5).height(46.dp),
            onValueChange = { newPwd = it },
            value = newPwd,
            hint = stringResource(id = R.string.modify_password_new_password)
        )
        Spacer(modifier = Modifier.padding(top = 18.dp))
        FMTextField(
            modifier = Modifier.background(AppColors.pink5).height(46.dp),
            onValueChange = { newPwdCheck = it },
            value = newPwdCheck,
            hint = stringResource(id = R.string.modify_password_new_password_check)
        )
        Spacer(modifier = Modifier.padding(top = 67.dp))
        FMButton(
            modifier = Modifier.fillMaxWidth().height(59.dp),
            onClick = { /** 비밀번호 변경 요청 **/ },
            text = stringResource(id = R.string.modify_password_btn)
        )
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

@Preview(showBackground = true)
@Composable
fun ModifyPasswordScreenPreview() {
    ModifyPasswordScreen()
}
