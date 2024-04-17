package io.familymoments.app.feature.profile.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.component.FMDropdownMenu
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.FileUtil
import io.familymoments.app.feature.profile.viewmodel.ProfileEditViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileEditScreen(
    navigateBack: () -> Unit,
    viewModel: ProfileEditViewModel,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val buttonEnabled = uiState.value.profileEditValidated.nameValidated &&
        uiState.value.profileEditValidated.nicknameValidated &&
        uiState.value.profileEditValidated.birthdateValidated
    val defaultProfileImageUri = Uri.parse("android.resource://${context.packageName}/${R.drawable.default_profile}")
    val scope = rememberCoroutineScope()
    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            navigateBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.edit_profile_title),
            style = AppTypography.B1_16,
            color = AppColors.black1,
            modifier = Modifier.padding(vertical = 18.dp)
        )
        ProfileImageRenderer(
            modifier = Modifier.padding(bottom = 22.dp),
            profileImage = uiState.value.profileImage
        )
        ProfileDropdown(
            defaultProfileImageUri = defaultProfileImageUri,
            onImageChanged = viewModel::imageChanged,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 34.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ProfileTextField(
                title = stringResource(id = R.string.profile_text_field_name),
                value = uiState.value.profileEditInfoUiState.name,
                hint = stringResource(id = R.string.profile_text_field_hint_name),
                onValueChanged = viewModel::nameChanged,
                showWarning = !uiState.value.profileEditValidated.nameValidated
            )
            ProfileTextField(
                title = stringResource(id = R.string.profile_text_field_nickname),
                value = uiState.value.profileEditInfoUiState.nickname,
                hint = stringResource(id = R.string.profile_text_field_hint_nickname),
                warning = stringResource(id = R.string.profile_nickname_warning),
                onValueChanged = viewModel::nicknameChanged,
                showWarning = !uiState.value.profileEditValidated.nicknameValidated
            )
            ProfileTextField(
                title = stringResource(id = R.string.profile_text_field_birth_date),
                value = uiState.value.profileEditInfoUiState.birthdate,
                hint = stringResource(id = R.string.profile_text_field_hint_birth_date),
                warning = stringResource(id = R.string.profile_birthdate_warning),
                onValueChanged = viewModel::birthdateChanged,
                showWarning = !uiState.value.profileEditValidated.birthdateValidated
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp),
        ) {
            ProfileButton(
                modifier = Modifier.weight(1f),
                onClick = navigateBack,
                colors = ButtonDefaults.buttonColors(AppColors.purple1, Color.White),
                stringResId = R.string.profile_btn_cancel
            )
            Spacer(modifier = Modifier.width(34.dp))
            ProfileButton(
                modifier = Modifier.weight(1f),
                onClick = { onButtonClicked(scope, context, uiState.value.profileImage, viewModel::editUserProfile) },
                enabled = buttonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.purple2,
                    contentColor = AppColors.grey6,
                    disabledContainerColor = AppColors.grey3,
                    disabledContentColor = AppColors.grey6
                ),
                stringResId = R.string.profile_btn_done
            )
        }
    }
}

private fun onButtonClicked(
    scope: CoroutineScope,
    context: Context,
    uri: Uri,
    editUserProfile: (File) -> Unit = {}
) {
    scope.launch {
        val imageFile = FileUtil.imageFileResize(context, uri)
        editUserProfile(imageFile)
    }
}

@Composable
fun ProfileImageRenderer(
    modifier: Modifier = Modifier,
    profileImage: Uri,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            AsyncImage(
                model = profileImage,
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
private fun ProfileTextField(
    title: String = "",
    value: String = "",
    hint: String = "",
    warning: String = "",
    showWarning: Boolean = false,
    onValueChanged: (String) -> Unit = {},
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    Column {
        Text(
            text = title,
            style = AppTypography.B1_16,
            color = AppColors.deepPurple1,
            modifier = Modifier.padding(start = 1.dp, bottom = 2.dp)
        )
        FMTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onValueChanged(newValue.text)
            },
            hint = hint,
            textColor = if (showWarning) AppColors.red2 else AppColors.black2,
            hintColor = if (showWarning) AppColors.red2 else AppColors.grey2,
            borderColor = if (showWarning) AppColors.red2 else AppColors.grey2
        )
        if (showWarning) {
            WarningText(
                modifier = Modifier
                    .height(42.dp)
                    .padding(top = 3.dp),
                    warning = warning,
                showText = showWarning
            )
        } else {
            Spacer(modifier = Modifier.height(42.dp))
        }
    }
}

@Composable
fun WarningText(
    modifier: Modifier = Modifier,
    showText: Boolean = false,
    warning: String = ""
) {
    if (!showText) return
    Text(
        modifier = modifier.padding(top = 3.dp),
        text = warning,
        style = AppTypography.LB2_11,
        color = AppColors.red2
    )
}

@Composable
fun ProfileDropdown(
    defaultProfileImageUri: Uri,
    onImageChanged: (Uri) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            if (it == null) return@rememberLauncherForActivityResult
            onImageChanged(it)
        }
    )
    FMDropdownMenu(
        onGallerySelected = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onDefaultImageSelected = {
            onImageChanged(defaultProfileImageUri)
        }
    )
}

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    colors: ButtonColors,
    @StringRes stringResId: Int,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(54.dp),
        enabled = enabled,
        colors = colors,
        shape = RoundedCornerShape(60.dp)
    ) {
        Text(
            text = stringResource(id = stringResId),
            style = AppTypography.BTN4_18,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview() {

}
