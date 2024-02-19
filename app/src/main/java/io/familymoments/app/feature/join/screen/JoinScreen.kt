package io.familymoments.app.feature.join.screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.feature.join.model.request.CheckEmailRequest
import io.familymoments.app.feature.join.model.request.CheckIdRequest
import io.familymoments.app.feature.join.model.request.JoinRequest
import io.familymoments.app.feature.join.model.response.CheckEmailResponse
import io.familymoments.app.feature.join.model.response.CheckIdResponse
import io.familymoments.app.feature.join.model.response.JoinResponse
import io.familymoments.app.feature.join.model.JoinInfoUiModel
import io.familymoments.app.feature.join.model.JoinTermUiModel
import io.familymoments.app.core.network.api.PublicService
import io.familymoments.app.core.component.FMCheckBox
import io.familymoments.app.core.component.CheckedStatus
import io.familymoments.app.core.component.JoinTextFieldArea
import io.familymoments.app.feature.join.viewmodel.JoinViewModel
import io.familymoments.app.core.component.ShowWarningText
import io.familymoments.app.core.network.repository.impl.PublicRepositoryImpl
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.FamilyMomentsTheme
import okhttp3.MultipartBody

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JoinScreen(viewModel: JoinViewModel) {
    LazyColumn(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ) {
        item {
            JoinContentScreen(viewModel)
        }
    }
}


@Composable
fun JoinContentScreen(viewModel: JoinViewModel) {
    val context = LocalContext.current
    var password: String by remember { mutableStateOf("") }
    val bitmap: Bitmap = getDefaultProfileImageBitmap(context)
    var allEssentialTermsAgree by remember {
        mutableStateOf(false)
    }
    val userIdDuplicationCheck = viewModel.userIdDuplicationCheck.collectAsStateWithLifecycle()
    val emailDuplicationCheck = viewModel.emailDuplicationCheck.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = userIdDuplicationCheck.value) {
        if (userIdDuplicationCheck.value == true) {
            Toast.makeText(
                context,
                context.getString(R.string.join_check_user_id_duplication_success),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (userIdDuplicationCheck.value == false) {
            Toast.makeText(context, context.getString(R.string.join_check_user_id_duplication_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

    LaunchedEffect(key1 = emailDuplicationCheck.value) {
        if (emailDuplicationCheck.value == true) {
            Toast.makeText(
                context,
                context.getString(R.string.join_check_email_duplication_success),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (emailDuplicationCheck.value == false) {
            Toast.makeText(context, context.getString(R.string.join_check_email_duplication_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

    var passwordSameCheck by remember {
        mutableStateOf(false)
    }
    var joinInfoUiModel: JoinInfoUiModel by remember {
        mutableStateOf(
            JoinInfoUiModel(bitmap = bitmap)
        )
    }

    Column {
        Spacer(modifier = Modifier.height(40.dp))
        IdField(viewModel = viewModel) { joinInfoUiModel = joinInfoUiModel.copy(id = it) }
        PasswordField(viewModel = viewModel, { passwordSameCheck = it }) {
            password = it
            joinInfoUiModel = joinInfoUiModel.copy(password = it)
        }
        NameField { joinInfoUiModel = joinInfoUiModel.copy(name = it) }
        EmailField(viewModel) { joinInfoUiModel = joinInfoUiModel.copy(email = it) }
        BirthDayField { joinInfoUiModel = joinInfoUiModel.copy(birthDay = it) }
        NicknameField(viewModel) { joinInfoUiModel = joinInfoUiModel.copy(nickname = it) }
        ProfileImageField {
            joinInfoUiModel = joinInfoUiModel.copy(bitmap = it)
        }
        Spacer(modifier = Modifier.height(53.dp))
        TermsField { allEssentialTermsAgree = it }
        StartButtonField(
            viewModel::join,
            joinInfoUiModel,
            userIdDuplicationCheck.value ?: false,
            passwordSameCheck,
            emailDuplicationCheck.value ?: false,
            allEssentialTermsAgree
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun IdField(viewModel: JoinViewModel, onValueChange: (String) -> Unit) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    var textFieldBorderColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var textColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    val userIdValidation = viewModel.userIdFormatValidated.collectAsStateWithLifecycle()
    Column {
        JoinTextFieldArea(
            modifier = Modifier.onFocusChanged {
                textFieldBorderColor = if (it.hasFocus) AppColors.purple2 else AppColors.grey2
            },
            title = stringResource(R.string.join_id_field_title),
            hint = stringResource(R.string.join_id_field_hint),
            onValueChange = {
                textFieldValue = it
                onValueChange(it.text)
                viewModel.checkIdFormat(it.text)
            },
            showCheckButton = true,
            checkButtonAvailable = userIdValidation.value,
            onCheckButtonClick = {
                viewModel.checkIdDuplication(it.text)
            },
            borderColor = textFieldBorderColor,
            textColor = textColor
        )
        ShowWarningText(
            text = stringResource(id = R.string.join_id_validation_warning),
            textFieldValue = textFieldValue,
            validation = userIdValidation.value,
            colorChange = {
                textColor = it
                textFieldBorderColor = it
            })
    }
    JoinTextFieldVerticalSpacer()
}

@Composable
fun PasswordField(viewModel: JoinViewModel, onPasswordSameCheck: (Boolean) -> Unit, onValueChange: (String) -> Unit) {
    val passwordValidation = viewModel.passwordFormatValidated.collectAsStateWithLifecycle()
    var firstPwd by remember {
        mutableStateOf(TextFieldValue())
    }
    var secondPwd by remember {
        mutableStateOf(TextFieldValue())
    }
    var firstPwdTextFieldBorderColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var firstPwdTextColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var secondPwdTextFieldBorderColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var secondPwdTextColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    Column {
        JoinTextFieldArea(
            title = stringResource(id = R.string.join_password_field_title),
            hint = stringResource(R.string.join_password_field_hint),
            onValueChange = {
                firstPwd = it
                onValueChange(it.text)
                viewModel.checkPasswordFormat(it.text)
            },
            showDeleteButton = true,
            borderColor = firstPwdTextFieldBorderColor,
            textColor = firstPwdTextColor
        )
        ShowWarningText(
            text = stringResource(id = R.string.join_password_validation_warning),
            textFieldValue = firstPwd,
            validation = passwordValidation.value,
            colorChange = {
                firstPwdTextColor = it
                firstPwdTextFieldBorderColor = it
            })
        Spacer(modifier = Modifier.height(20.dp))
        JoinTextFieldArea(
            title = stringResource(id = R.string.join_password_check_field_title),
            hint = stringResource(R.string.join_password_check_field_hint),
            onValueChange = {
                secondPwd = it
                onPasswordSameCheck(firstPwd.text == it.text)
            },
            showDeleteButton = true,
            borderColor = secondPwdTextFieldBorderColor,
            textColor = secondPwdTextColor
        )
        ShowWarningText(
            text = stringResource(id = R.string.join_password_check_validation_warning),
            textFieldValue = secondPwd,
            validation = firstPwd.text == secondPwd.text,
            colorChange = {
                secondPwdTextColor = it
                secondPwdTextFieldBorderColor = it
            })
    }
    JoinTextFieldVerticalSpacer()
}

@Composable
fun NameField(onValueChange: (String) -> Unit) {
    JoinTextFieldArea(
        title = stringResource(id = R.string.join_name_field_title),
        hint = stringResource(R.string.join_name_field_hint),
        onValueChange = { onValueChange(it.text) })

    JoinTextFieldVerticalSpacer()
}

@Composable
fun EmailField(viewModel: JoinViewModel, onValueChange: (String) -> Unit) {
    val emailValidation = viewModel.emailFormatValidated.collectAsStateWithLifecycle()
    var email by remember {
        mutableStateOf(TextFieldValue())
    }
    var textFieldBorderColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var textColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    Column {
        JoinTextFieldArea(
            title = stringResource(R.string.join_email_field_title),
            hint = stringResource(id = R.string.join_email_field_hint),
            onValueChange = {
                email = it
                onValueChange(it.text)
                viewModel.checkEmailFormat(it.text)
            },
            showCheckButton = true,
            checkButtonAvailable = emailValidation.value,
            onCheckButtonClick = {
                viewModel.checkEmailDuplication(it.text)
            },
            borderColor = textFieldBorderColor,
            textColor = textColor
        )
        ShowWarningText(
            text = stringResource(id = R.string.join_password_check_validation_warning),
            textFieldValue = email,
            validation = emailValidation.value,
            colorChange = {
                textColor = it
                textFieldBorderColor = it
            })
    }
    JoinTextFieldVerticalSpacer()
}

@Composable
fun BirthDayField(onTextFieldChange: (String) -> Unit) {
    JoinTextFieldArea(
        title = stringResource(id = R.string.join_birthday_field_title),
        hint = stringResource(R.string.join_birthday_field_hint),
        onValueChange = { onTextFieldChange(it.text) })

    JoinTextFieldVerticalSpacer()
}

@Composable
fun NicknameField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val nicknameValidation = viewModel.nicknameFormatValidated.collectAsStateWithLifecycle()
    var nickname by remember {
        mutableStateOf(TextFieldValue())
    }
    var textFieldBorderColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    var textColor by remember {
        mutableStateOf(AppColors.grey2)
    }
    Column {
        JoinTextFieldArea(
            title = stringResource(id = R.string.join_nickname_field_title),
            hint = stringResource(R.string.join_nickname_field_hint),
            onValueChange = {
                nickname = it
                onTextFieldChange(it.text)
                viewModel.checkNicknameFormat(it.text)
            })
        ShowWarningText(
            text = stringResource(id = R.string.join_nickname_validation_warning),
            textFieldValue = nickname,
            validation = nicknameValidation.value,
            colorChange = {
                textColor = it
                textFieldBorderColor = it
            })
    }
    JoinTextFieldVerticalSpacer()
}

@Composable
fun ProfileImageSelectDropDownMenu(
    isMenuExpanded: Boolean,
    isMenuExpandedChanged: (Boolean) -> Unit,
    getBitmap: (Bitmap?) -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        bitmap = convertToBitmap(context, it)
        getBitmap(bitmap)
    }
    DropdownMenu(expanded = isMenuExpanded,
        onDismissRequest = { isMenuExpandedChanged(false) }) {
        MenuItemGallerySelect(launcher, isMenuExpandedChanged)
        MenuItemDefaultImage({ getBitmap(it) }, context, isMenuExpandedChanged)
    }
}

@Composable
fun MenuItemGallerySelect(
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    isMenuExpandedChanged: (Boolean) -> Unit
) {
    DropdownMenuItem(onClick = {
        launcher.launch(
            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
        isMenuExpandedChanged(false)
    }) {
        Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_gallery))
    }
}

@Composable
fun MenuItemDefaultImage(getBitmap: (Bitmap) -> Unit, context: Context, isMenuExpandedChanged: (Boolean) -> Unit) {
    DropdownMenuItem(onClick = {
        getBitmap(getDefaultProfileImageBitmap(context))
        isMenuExpandedChanged(false)
    }) {
        Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_default_image))
    }
}

fun getDefaultProfileImageBitmap(context: Context): Bitmap {
    return BitmapFactory.decodeResource(context.resources, R.drawable.default_profile)
}

fun convertToBitmap(context: Context, uri: Uri?): Bitmap? {
    var bitmap: Bitmap? = null
    uri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }
    return bitmap
}

@Composable
fun ProfileImageField(onBitmapChange: (Bitmap) -> Unit) {
    var isMenuExpanded: Boolean by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Text(
        text = stringResource(R.string.join_select_profile_image_title),
        color = Color(0xFF5B6380),
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(7.dp))
    Button(
        modifier = Modifier.height(150.dp),
        onClick = { isMenuExpanded = !isMenuExpanded },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF3F4F7),
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
    ) {
        ProfileImageSelectDropDownMenu(isMenuExpanded, { isMenuExpanded = it }) { bitmap = it }
        bitmap?.let {
            onBitmapChange(it)
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.padding(bottom = 2.dp),
                painter = painterResource(id = R.drawable.ic_select_pic),
                contentDescription = null,
            )
            Text(text = stringResource(R.string.join_select_profile_image_btn), color = Color(0xFFBFBFBF))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.join_select_profile_image_description),
        color = Color(0xFFA9A9A9),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(
                Alignment.Center,
            ),
    )
    JoinTextFieldVerticalSpacer()
}

@Composable
fun StartButtonField(
    onClick: (JoinInfoUiModel) -> Unit,
    joinInfoUiModel: JoinInfoUiModel,
    idDuplicationCheck: Boolean,
    passwordSameCheck: Boolean,
    emailDuplicationCheck: Boolean,
    allEssentialTermsAgree: Boolean
) {
    var joinEnable by remember {
        mutableStateOf(false)
    }
    joinEnable = idDuplicationCheck && passwordSameCheck && emailDuplicationCheck && allEssentialTermsAgree
    FMButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(joinInfoUiModel) },
        enabled = joinEnable,
        text = stringResource(R.string.join_btn)
    )
}

@Composable
fun TermItem(
    imageResources: List<Int>,
    description: Int,
    checked: CheckedStatus,
    fontSize: Int,
    onCheckedChange: (CheckedStatus) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        FMCheckBox(
            imageResources = imageResources,
            defaultStatus = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            modifier = Modifier.padding(start = 14.dp),
            text = stringResource(id = description),
            fontSize = fontSize.sp
        )
    }
}

@Composable
fun TermsList(
    list: List<JoinTermUiModel>,
    onTermCheckedChange: (Int, CheckedStatus) -> Unit,
    onTermsCheckedChange: (Boolean) -> Unit
) {
    onTermsCheckedChange(list.filter { it.isEssential }.all { it.checkedStatus == CheckedStatus.CHECKED })
    for (index in list.indices) {
        TermItem(
            imageResources = listOf(R.drawable.uncheck, R.drawable.check),
            description = list[index].description,
            checked = list[index].checkedStatus,
            fontSize = 13,
            onCheckedChange = { onTermCheckedChange(index, it) })
    }
}

@Composable
fun TermsField(onAllEssentialTermsAgree: (Boolean) -> Unit) {

    val terms = remember {
        mutableStateListOf(
            JoinTermUiModel(true, R.string.join_service_term_agree, CheckedStatus.UNCHECKED),
            JoinTermUiModel(true, R.string.join_identification_term_agree, CheckedStatus.UNCHECKED),
            JoinTermUiModel(false, R.string.join_marketing_alarm_term_agree, CheckedStatus.UNCHECKED)
        )
    }

    Column {
        TermItem(
            imageResources = listOf(R.drawable.circle_uncheck, R.drawable.circle_check),
            description = R.string.join_all_term_agree,
            checked = if (terms.all { it.checkedStatus == CheckedStatus.CHECKED }) CheckedStatus.CHECKED else CheckedStatus.UNCHECKED,
            fontSize = 16,
            onCheckedChange = {
                for (index in terms.indices) {
                    terms[index] = terms[index].copy(checkedStatus = it)
                }
            })
        Divider(modifier = Modifier.padding(vertical = 11.dp), thickness = 1.dp, color = AppColors.grey2)
        TermsList(list = terms, { index, checkedStatus ->
            terms[index] = terms[index].copy(checkedStatus = checkedStatus)
        }) { onAllEssentialTermsAgree(it) }
    }
    JoinTextFieldVerticalSpacer()
}


@Composable
fun JoinTextFieldVerticalSpacer() {
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewJoinScreen() {
    FamilyMomentsTheme {
        JoinScreen(JoinViewModel(PublicRepositoryImpl(object : PublicService {
            override suspend fun checkId(checkIdRequest: CheckIdRequest): CheckIdResponse {
                return CheckIdResponse(true, 200, "", "")
            }

            override suspend fun checkEmail(checkEmailRequest: CheckEmailRequest): CheckEmailResponse {
                return CheckEmailResponse(true, 200, "", "")
            }

            override suspend fun join(profileImg: MultipartBody.Part, joinRequest: JoinRequest): JoinResponse {
                TODO("Not yet implemented")
            }

        })))
    }
}
