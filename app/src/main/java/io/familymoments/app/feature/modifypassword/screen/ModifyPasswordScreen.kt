package io.familymoments.app.feature.modifypassword.screen

import android.content.res.Resources
import android.view.ViewTreeObserver
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ModifyPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ModifyPasswordViewModel
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var keyboardHeight by remember { mutableFloatStateOf(0f) }
    KeyboardHeightListener { height ->
        val adjustedHeight = if (height < 0) 0 else height
        keyboardHeight = pixelsToDp(adjustedHeight)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        ModifyPasswordTitle()
        Spacer(modifier = Modifier.height(40.dp))
        ModifyPasswordInfo()
        Spacer(modifier = Modifier.height(26.dp))
        CurrentPasswordField(viewModel = viewModel)
        NewPasswordField(viewModel = viewModel, scope = scope, scrollState = scrollState)
        ModifyPasswordButton(viewModel = viewModel)
        Spacer(modifier = Modifier.height(keyboardHeight.dp + 20.dp))
    }
}

@Composable
fun KeyboardHeightListener(onKeyboardHeightChanged: (Int) -> Unit) {
    val context = LocalContext.current
    val rootView = LocalView.current

    DisposableEffect(context, rootView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keyboardHeight = screenHeight - rect.bottom
            onKeyboardHeightChanged(keyboardHeight)
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}

fun pixelsToDp(pixels: Int): Float {
    val density = Resources.getSystem().displayMetrics.density
    return pixels / density
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

    ModifyPasswordTextField(
        onValueChange = {
            currentPassword = it
            viewModel.checkCurrentPassword(it.text)
        },
        value = currentPassword,
        hintResId = R.string.modify_password_current_password,
        borderColor = if (currentPasswordValid) AppColors.grey2 else AppColors.red2
    )
    ModifyPasswordWarning(
        warningResId = currentPasswordWarning?.stringResId,
        bottomPadding = 70.dp
    )
}

@Composable
private fun NewPasswordField(
    viewModel: ModifyPasswordViewModel,
    scope: CoroutineScope,
    scrollState: ScrollState
) {
    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var newPasswordCheck by remember { mutableStateOf(TextFieldValue()) }
    val newPasswordWarning = viewModel.newPasswordWarning.collectAsStateWithLifecycle().value
    val onFocusChange: (Boolean) -> Unit = { isFocused ->
        if (isFocused) {
            scope.launch {
                delay(300)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }
    ModifyPasswordTextField(
        onValueChange = {
            newPassword = it
            viewModel.checkNewPassword(it.text, newPasswordCheck.text)
        },
        value = newPassword,
        hintResId = R.string.modify_password_new_password,
        borderColor = if (newPasswordWarning == null) AppColors.grey2 else AppColors.red2,
        onFocusChange = onFocusChange
    )
    Spacer(modifier = Modifier.padding(top = 18.dp))
    ModifyPasswordTextField(
        onValueChange = {
            newPasswordCheck = it
            viewModel.checkNewPassword(newPassword.text, it.text)
        },
        value = newPasswordCheck,
        hintResId = R.string.modify_password_new_password_check,
        borderColor = if (newPasswordWarning == null) AppColors.grey2 else AppColors.red2,
        onFocusChange = onFocusChange
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
fun ModifyPasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    value: TextFieldValue,
    @StringRes hintResId: Int,
    borderColor: Color,
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
        borderColor = borderColor,
        showDeleteButton = false,
        showText = false,
        onFocusChanged = onFocusChange,
        keyboardActions = keyboardActions
    )
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
