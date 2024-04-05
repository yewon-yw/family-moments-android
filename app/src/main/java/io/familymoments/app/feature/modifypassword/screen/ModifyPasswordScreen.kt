package io.familymoments.app.feature.modifypassword.screen

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.login.activity.LoginActivity
import io.familymoments.app.feature.modifypassword.model.uistate.CurrentPasswordUiState
import io.familymoments.app.feature.modifypassword.model.uistate.ModifyPasswordUiState
import io.familymoments.app.feature.modifypassword.model.uistate.NewPasswordCheckUiState
import io.familymoments.app.feature.modifypassword.model.uistate.NewPasswordUiState
import io.familymoments.app.feature.modifypassword.viewmodel.ModifyPasswordViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifyPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ModifyPasswordViewModel
) {
    val requester = remember { BringIntoViewRequester() }
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val isNewPasswordValidated =
        uiState.value.newPasswordUiState.isValidated && uiState.value.newPasswordCheckUiState.isValidated

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            Toast.makeText(context, context.getString(R.string.modify_password_request_success), Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ModifyPasswordTitle()
        Spacer(modifier = Modifier.height(40.dp))
        ModifyPasswordInfo()
        Spacer(modifier = Modifier.height(26.dp))
        CurrentPasswordField(
            currentPasswordUiState = uiState.value.currentPasswordUiState,
            checkCurrentPassword = viewModel::checkCurrentPassword,
            updatePasswordUiState = viewModel::updateCurrentPassword,
            onClearCurrentPassword = viewModel::onClearCurrentPassword,
        )
        NewPasswordField(
            newPasswordUiState = uiState.value.newPasswordUiState,
            newPasswordCheckUiState = uiState.value.newPasswordCheckUiState,
            checkPasswordFormat = viewModel::checkPasswordFormat,
            checkPasswordEqual = viewModel::checkPasswordEqual,
            updatePasswordUiState = viewModel::updateNewPassword,
            onClearNewPasswords = viewModel::onClearNewPasswords,
            requester = requester,
        )
        ModifyPasswordButton(
            currentPasswordValid = uiState.value.currentPasswordUiState.isValidated,
            newPasswordValid = isNewPasswordValidated,
            requester = requester,
            onClick = { modifyPasswordUiState ->
                viewModel.requestModifyPassword(modifyPasswordUiState)
            },
            uiState = uiState.value
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ModifyPasswordTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
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
private fun CurrentPasswordField(
    currentPasswordUiState: CurrentPasswordUiState,
    checkCurrentPassword: (String) -> Unit,
    updatePasswordUiState: (String) -> Unit,
    onClearCurrentPassword: () -> Unit
) {
    var currentPassword by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(currentPasswordUiState.isReset) {
        if (currentPasswordUiState.isReset) {
            currentPassword = TextFieldValue()
            onClearCurrentPassword()
        }
    }
    ModifyPasswordTextField(
        onValueChange = {
            currentPassword = it
            checkCurrentPassword(it.text)
            updatePasswordUiState(it.text)
        },
        value = currentPassword,
        hintResId = R.string.modify_password_current_password,
        showWarning = currentPasswordUiState.warningResId != null
    )
    ModifyPasswordWarning(
        warningResId = currentPasswordUiState.warningResId,
        bottomPadding = 70.dp,
        hideText = currentPasswordUiState.warningResId == null
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewPasswordField(
    newPasswordUiState: NewPasswordUiState,
    newPasswordCheckUiState: NewPasswordCheckUiState,
    checkPasswordFormat: (String) -> Unit,
    checkPasswordEqual: (String, String) -> Unit,
    updatePasswordUiState: (String, String) -> Unit,
    onClearNewPasswords: () -> Unit,
    requester: BringIntoViewRequester,
) {
    val scope = rememberCoroutineScope()
    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var newPasswordCheck by remember { mutableStateOf(TextFieldValue()) }
    val onFocusChange: (Boolean) -> Unit = { isFocused ->
        scope.launch {
            if (isFocused) {
                delay(300)
                requester.bringIntoView()
            }
        }
    }
    LaunchedEffect(newPasswordUiState.isReset, newPasswordCheckUiState.isReset) {
        if (newPasswordUiState.isReset && newPasswordCheckUiState.isReset) {
            newPassword = TextFieldValue()
            newPasswordCheck = TextFieldValue()
            onClearNewPasswords()
        }
    }
    ModifyPasswordTextField(
        onValueChange = {
            newPassword = it
            checkPasswordFormat(it.text)
            checkPasswordEqual(it.text, newPasswordCheck.text)
            updatePasswordUiState(it.text, newPasswordCheck.text)
        },
        value = newPassword,
        hintResId = R.string.modify_password_new_password,
        showWarning = newPasswordUiState.showWarningBorder,
        onFocusChange = onFocusChange,
    )
    Spacer(modifier = Modifier.padding(top = 18.dp))
    ModifyPasswordTextField(
        onValueChange = {
            newPasswordCheck = it
            checkPasswordFormat(newPassword.text)
            checkPasswordEqual(newPassword.text, it.text)
            updatePasswordUiState(it.text, newPasswordCheck.text)
        },
        value = newPasswordCheck,
        hintResId = R.string.modify_password_new_password_check,
        showWarning = newPasswordCheckUiState.showWarningBorder,
        onFocusChange = onFocusChange
    )
    ModifyPasswordWarning(
        warningResId = newPasswordUiState.warningResId,
        bottomPadding = 67.dp,
        hideText = newPasswordUiState.warningResId == null
    )
}

@Composable
fun ModifyPasswordWarning(
    @StringRes warningResId: Int?,
    bottomPadding: Dp = 0.dp,
    hideText: Boolean = true
) {
    if (hideText) {
        Spacer(modifier = Modifier.padding(top = bottomPadding))
    } else {
        Box(
            modifier = Modifier
                .padding(top = 9.dp, bottom = bottomPadding - 25.dp)
        ) {
            if (warningResId != null) {
                Text(
                    text = stringResource(id = warningResId),
                    style = AppTypography.LB1_13,
                    color = AppColors.red2,
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}

@Composable
fun ModifyPasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    value: TextFieldValue,
    @StringRes hintResId: Int,
    showWarning: Boolean,
    onFocusChange: (Boolean) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onDone = { focusManager.clearFocus() }
    )
    FMTextField(
        modifier = modifier
            .background(AppColors.pink5)
            .height(46.dp),
        onValueChange = onValueChange,
        value = value,
        hint = stringResource(id = hintResId),
        borderColor = if (showWarning) AppColors.red2 else AppColors.grey2,
        showDeleteButton = false,
        showText = false,
        onFocusChanged = onFocusChange,
        keyboardActions = keyboardActions
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifyPasswordButton(
    currentPasswordValid: Boolean,
    newPasswordValid: Boolean,
    requester: BringIntoViewRequester,
    onClick: (ModifyPasswordUiState) -> Unit,
    uiState: ModifyPasswordUiState
) {
    FMButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp)
            .bringIntoViewRequester(requester),
        onClick = { onClick(uiState) },
        text = stringResource(id = R.string.modify_password_btn),
        enabled = currentPasswordValid && newPasswordValid
    )
}

@Preview(showBackground = true)
@Composable
fun ModifyPasswordScreenPreview() {
    ModifyPasswordScreen(viewModel = hiltViewModel())
}
