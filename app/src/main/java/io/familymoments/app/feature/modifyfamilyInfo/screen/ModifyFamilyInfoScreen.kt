package io.familymoments.app.feature.modifyfamilyInfo.screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.component.ImageSelectionMenu
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.URI_SCHEME_RESOURCE
import io.familymoments.app.core.util.noRippleClickable
import io.familymoments.app.feature.modifyfamilyInfo.viewmodel.ModifyFamilyInfoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModifyFamilyInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: ModifyFamilyInfoViewModel
) {
    val showDialog = remember { mutableStateOf(false) }
    val familyName = remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val requester = remember { BringIntoViewRequester() }

    val defaultProfileImageUri =
        Uri.parse("$URI_SCHEME_RESOURCE://${context.packageName}/${R.drawable.default_profile}")
    val launcher = generateVisualMediaRequestLauncher { uri ->
        viewModel.updateRepresentImg(context, uri)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getFamilyInfo()
    }
    LaunchedEffect(uiState.value) {
        if (uiState.value.isSuccess) {
            familyName.value = TextFieldValue(uiState.value.familyName)
        }
    }

    if (showDialog.value) {
        ImageSelectionMenu(
            onDismissRequest = { showDialog.value = false },
            onGallerySelected = {
                launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onDefaultImageSelected = {
                viewModel.updateRepresentImg(context, defaultProfileImageUri)
            }
        )
    }

    ModifyFamilyInfoScreenUI(
        modifier = modifier.bringIntoViewRequester(requester),
        onEditImageClicked = { showDialog.value = true },
        onFamilyNameChanged = { familyName.value = it },
        onFocusChanged = {
            scope.launch {
                delay(300)
                requester.bringIntoView()
            }
        },
        familyName = familyName.value,
        representImg = uiState.value.representImg,
        focusManager = focusManager,
        onDoneButtonClicked = {
            onDoneButtonClicked(
                context = context,
                representImg = uiState.value.representImg,
                familyName = familyName.value.text,
                modifyFamilyInfo = viewModel::modifyFamilyInfo
            )
        }
    )
}

@Composable
fun ModifyFamilyInfoScreenUI(
    modifier: Modifier = Modifier,
    onEditImageClicked: () -> Unit = {},
    onFamilyNameChanged: (TextFieldValue) -> Unit = {},
    onDoneButtonClicked: () -> Unit = {},
    familyName: TextFieldValue,
    representImg: File? = null,
    focusManager: FocusManager = LocalFocusManager.current,
    onFocusChanged: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = stringResource(id = R.string.modify_family_info_title),
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        AsyncImage(
            model = representImg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 15.dp)
                .size(110.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(id = R.string.modify_family_info_edit_image),
            style = AppTypography.B1_16,
            color = AppColors.purple2,
            modifier = Modifier
                .padding(bottom = 18.dp)
                .noRippleClickable {
                    focusManager.clearFocus()
                    onEditImageClicked()
                }
        )
        HorizontalDivider(color = AppColors.grey3, thickness = 0.6.dp)
        Text(
            text = stringResource(id = R.string.modify_family_info_family_name),
            style = AppTypography.B1_16,
            color = AppColors.deepPurple1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 20.dp, bottom = 9.dp)
        )
        FMTextField(
            modifier = Modifier
                .height(46.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocusChanged()
                    }
                },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onDoneButtonClicked()
            }),
            onValueChange = onFamilyNameChanged,
            value = familyName,
            hint = "",
            textColor = AppColors.black2
        )
        Button(
            onClick = onDoneButtonClicked,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 187.dp)
                .height(59.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.deepPurple1,
                disabledContainerColor = AppColors.grey3,
                disabledContentColor = AppColors.grey6
            ),
            enabled = familyName.text.trim().isNotEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.modify_family_info_btn),
                style = AppTypography.BTN4_18,
                color = AppColors.grey6
            )
        }
    }
}

@Composable
private fun generateVisualMediaRequestLauncher(updateImage: (Uri) -> Unit) =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            updateImage(uri)
        }
    }

fun onDoneButtonClicked(
    context: Context,
    representImg: File?,
    familyName: String,
    modifyFamilyInfo: (File, String) -> Unit
) {
    if (representImg == null) {
        Toast.makeText(context, R.string.modify_family_info_image_error, Toast.LENGTH_SHORT).show()
    } else {
        modifyFamilyInfo(representImg, familyName)
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyFamilyInfoScreenPreview() {
    ModifyFamilyInfoScreenUI(
        familyName = TextFieldValue("sweety home"),
    )
}
