package io.familymoments.app.feature.signup.screen

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import io.familymoments.app.core.component.AppBarScreen
import io.familymoments.app.core.component.CheckedStatus
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMCheckBox
import io.familymoments.app.core.component.SignUpTextFieldArea
import io.familymoments.app.core.network.api.SignInService
import io.familymoments.app.core.network.repository.impl.SignInRepositoryImpl
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.core.util.convertUriToBitmap
import io.familymoments.app.feature.signup.model.SignUpInfoUiModel
import io.familymoments.app.feature.signup.model.SignUpTermUiModel
import io.familymoments.app.feature.signup.model.request.CheckEmailRequest
import io.familymoments.app.feature.signup.model.request.CheckIdRequest
import io.familymoments.app.feature.signup.model.request.SignUpRequest
import io.familymoments.app.feature.signup.model.response.CheckEmailResponse
import io.familymoments.app.feature.signup.model.response.CheckIdResponse
import io.familymoments.app.feature.signup.model.response.SignUpResponse
import io.familymoments.app.feature.signup.viewmodel.SignUpViewModel
import okhttp3.MultipartBody

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    val context = LocalContext.current
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.sign_up_activity_app_bar_title),
                style = AppTypography.SH3_16,
                color = AppColors.deepPurple1
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    (context as Activity).finish()
                },
                painter = painterResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }) {
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .padding(horizontal = 20.dp)
        ) {
            item {
                SignUpContentScreen(
                    viewModel,
                    context
                )
            }
        }
    }
}


@Composable
fun SignUpContentScreen(
    viewModel: SignUpViewModel,
    context: Context
) {
    val signUpFormatValidatedUiState = viewModel.signUpFormatValidatedUiState.collectAsStateWithLifecycle()
    val userIdDuplicationCheck = viewModel.userIdDuplicationCheck.collectAsStateWithLifecycle()
    val emailDuplicationCheck = viewModel.emailDuplicationCheck.collectAsStateWithLifecycle()

    val defaultProfileImageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.default_profile)
    showUserIdDuplicationCheckResult(userIdDuplicationCheck.value, context)
    showEmailDuplicationCheckResult(emailDuplicationCheck.value, context)

    var password: String by remember { mutableStateOf("") }
    val bitmap: Bitmap = defaultProfileImageBitmap
    var allEssentialTermsAgree by remember {
        mutableStateOf(false)
    }

    var passwordSameCheck by remember {
        mutableStateOf(false)
    }
    var signUpInfoUiModel: SignUpInfoUiModel by remember {
        mutableStateOf(
            SignUpInfoUiModel(bitmap = bitmap)
        )
    }

    Column {
        Spacer(modifier = Modifier.height(40.dp))
        IdField(
            userIdFormatValidated = signUpFormatValidatedUiState.value.userIdFormatValidated,
            checkIdFormat = viewModel::checkIdFormat,
            checkIdDuplication = viewModel::checkIdDuplication
        ) { signUpInfoUiModel = signUpInfoUiModel.copy(id = it) }
        FirstPasswordField(
            passwordFormatValidated = signUpFormatValidatedUiState.value.passwordFormatValidated,
            checkPasswordFormat = viewModel::checkPasswordFormat
        ) {
            password = it
            signUpInfoUiModel = signUpInfoUiModel.copy(password = it)
        }
        SecondPasswordField(
            firstPassword = signUpInfoUiModel.password,
            checkPasswordIsSame = { passwordSameCheck = it },
        )
        NameField { signUpInfoUiModel = signUpInfoUiModel.copy(name = it) }
        EmailField(
            checkEmailFormat = viewModel::checkEmailFormat,
            checkEmailDuplication = viewModel::checkEmailDuplication,
            emailFormatValidated = signUpFormatValidatedUiState.value.emailFormatValidated,
        ) { signUpInfoUiModel = signUpInfoUiModel.copy(email = it) }
        BirthDayField { signUpInfoUiModel = signUpInfoUiModel.copy(birthDay = it) }
        NicknameField(
            nicknameFormatValidated = signUpFormatValidatedUiState.value.nicknameFormatValidated,
            checkNicknameFormat = viewModel::checkNicknameFormat,
        ) { signUpInfoUiModel = signUpInfoUiModel.copy(nickname = it) }
        ProfileImageField(defaultProfileImageBitmap) {
            signUpInfoUiModel = signUpInfoUiModel.copy(bitmap = it)
        }
        Spacer(modifier = Modifier.height(53.dp))
        TermsField { allEssentialTermsAgree = it }
        StartButtonField(
            viewModel::executeSignUp,
            signUpInfoUiModel,
            viewModel.userIdDuplicationCheck.value ?: false,
            passwordSameCheck,
            viewModel.emailDuplicationCheck.value ?: false,
            allEssentialTermsAgree
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

private fun showUserIdDuplicationCheckResult(userIdDuplicationCheck: Boolean?, context: Context) {
    if (userIdDuplicationCheck == true) {
        Toast.makeText(
            context,
            context.getString(R.string.sign_up_check_user_id_duplication_success),
            Toast.LENGTH_SHORT
        ).show()
    }
    if (userIdDuplicationCheck == false) {
        Toast.makeText(context, context.getString(R.string.sign_up_check_user_id_duplication_fail), Toast.LENGTH_SHORT)
            .show()
    }
}

private fun showEmailDuplicationCheckResult(emailDuplicationCheck: Boolean?, context: Context) {

    if (emailDuplicationCheck == true) {
        Toast.makeText(
            context,
            context.getString(R.string.sign_up_check_email_duplication_success),
            Toast.LENGTH_SHORT
        ).show()
    }
    if (emailDuplicationCheck == false) {
        Toast.makeText(context, context.getString(R.string.sign_up_check_email_duplication_fail), Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun IdField(
    userIdFormatValidated: Boolean,
    checkIdFormat: (String) -> Unit,
    checkIdDuplication: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column {
        SignUpTextFieldArea(
            modifier = Modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            title = stringResource(R.string.sign_up_id_field_title),
            hint = stringResource(R.string.sign_up_id_field_hint),
            onValueChange = {
                onValueChange(it.text)
                checkIdFormat(it.text)
            },
            showCheckButton = true,
            checkButtonAvailable = userIdFormatValidated,
            onCheckButtonClick = {
                checkIdDuplication(it.text)
            },
            validated = userIdFormatValidated,
            showWarningText = true,
            warningText = stringResource(id = R.string.sign_up_id_validation_warning),
            isFocused = isFocused
        )
    }
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun FirstPasswordField(
    passwordFormatValidated: Boolean,
    checkPasswordFormat: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    SignUpTextFieldArea(
        modifier = Modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        title = stringResource(id = R.string.sign_up_password_field_title),
        hint = stringResource(R.string.sign_up_password_field_hint),
        onValueChange = {
            onValueChange(it.text)
            checkPasswordFormat(it.text)
        },
        showDeleteButton = true,
        showWarningText = true,
        warningText = stringResource(id = R.string.sign_up_password_validation_warning),
        validated = passwordFormatValidated,
        isFocused = isFocused
    )
    Spacer(modifier = Modifier.height(20.dp))
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun SecondPasswordField(
    firstPassword: String,
    checkPasswordIsSame: (Boolean) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var isPasswordSame by remember {
        mutableStateOf(false)
    }
    Column {
        SignUpTextFieldArea(
            modifier = Modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            title = stringResource(id = R.string.sign_up_password_check_field_title),
            hint = stringResource(R.string.sign_up_password_check_field_hint),
            onValueChange = {
                isPasswordSame = firstPassword == it.text
                checkPasswordIsSame(isPasswordSame)
            },
            showDeleteButton = true,
            showWarningText = true,
            warningText = stringResource(id = R.string.sign_up_password_check_validation_warning),
            validated = isPasswordSame,
            isFocused = isFocused
        )
    }
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun NameField(onValueChange: (String) -> Unit) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    SignUpTextFieldArea(
        modifier = Modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        title = stringResource(id = R.string.sign_up_name_field_title),
        hint = stringResource(R.string.sign_up_name_field_hint),
        onValueChange = { onValueChange(it.text) },
        isFocused = isFocused
    )

    SignUpTextFieldVerticalSpacer()
}

@Composable
fun EmailField(
    checkEmailFormat: (String) -> Unit,
    checkEmailDuplication: (String) -> Unit,
    emailFormatValidated: Boolean,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column {
        SignUpTextFieldArea(
            modifier = Modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            title = stringResource(R.string.sign_up_email_field_title),
            hint = stringResource(id = R.string.sign_up_email_field_hint),
            onValueChange = {
                onValueChange(it.text)
                checkEmailFormat(it.text)
            },
            showCheckButton = true,
            checkButtonAvailable = emailFormatValidated,
            onCheckButtonClick = {
                checkEmailDuplication(it.text)
            },
            isFocused = isFocused,
            showWarningText = true,
            warningText = stringResource(id = R.string.sign_up_password_check_validation_warning),
            validated = emailFormatValidated
        )
    }
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun BirthDayField(onTextFieldChange: (String) -> Unit) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    SignUpTextFieldArea(
        modifier = Modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        title = stringResource(id = R.string.sign_up_birthday_field_title),
        hint = stringResource(R.string.sign_up_birthday_field_hint),
        onValueChange = { onTextFieldChange(it.text) },
        isFocused = isFocused
    )

    SignUpTextFieldVerticalSpacer()
}

@Composable
fun NicknameField(
    nicknameFormatValidated: Boolean,
    checkNicknameFormat: (String) -> Unit,
    onTextFieldChange: (String) -> Unit
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var nickname by remember {
        mutableStateOf(TextFieldValue())
    }
    Column {
        SignUpTextFieldArea(
            modifier = Modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            title = stringResource(id = R.string.sign_up_nickname_field_title),
            hint = stringResource(R.string.sign_up_nickname_field_hint),
            onValueChange = {
                nickname = it
                onTextFieldChange(it.text)
                checkNicknameFormat(it.text)
            },
            isFocused = isFocused,
            showWarningText = true,
            warningText = stringResource(id = R.string.sign_up_nickname_validation_warning),
            validated = nicknameFormatValidated
        )
    }
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun ProfileImageSelectDropDownMenu(
    isMenuExpanded: Boolean,
    isMenuExpandedChanged: (Boolean) -> Unit,
    defaultProfileImageBitmap: Bitmap,
    getBitmap: (Bitmap?) -> Unit,
) {
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    //갤러리에서 사진 선택 후 bitmap 으로 변환
    val launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        bitmap = convertUriToBitmap(it, context)
        getBitmap(bitmap)
    }

    DropdownMenu(expanded = isMenuExpanded,
        onDismissRequest = { isMenuExpandedChanged(false) }) {
        MenuItemGallerySelect(launcher, isMenuExpandedChanged)
        MenuItemDefaultImage({ getBitmap(defaultProfileImageBitmap) }, isMenuExpandedChanged)
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
        Text(text = stringResource(R.string.sign_up_select_profile_image_drop_down_menu_gallery))
    }
}

@Composable
fun MenuItemDefaultImage(getDefaultProfileImageBitmap: () -> Unit, isMenuExpandedChanged: (Boolean) -> Unit) {
    DropdownMenuItem(onClick = {
        getDefaultProfileImageBitmap()
        isMenuExpandedChanged(false)
    }) {
        Text(text = stringResource(R.string.sign_up_select_profile_image_drop_down_menu_default_image))
    }
}

@Composable
fun ProfileImageField(defaultProfileImageBitmap: Bitmap, onBitmapChange: (Bitmap) -> Unit) {
    var isMenuExpanded: Boolean by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Text(
        text = stringResource(R.string.sign_up_select_profile_image_title),
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
        ProfileImageSelectDropDownMenu(isMenuExpanded, { isMenuExpanded = it }, defaultProfileImageBitmap) {
            bitmap = it
        }
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
            Text(text = stringResource(R.string.sign_up_select_profile_image_btn), color = Color(0xFFBFBFBF))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.sign_up_select_profile_image_description),
        color = Color(0xFFA9A9A9),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(
                Alignment.Center,
            ),
    )
    SignUpTextFieldVerticalSpacer()
}

@Composable
fun StartButtonField(
    onClick: (SignUpInfoUiModel) -> Unit,
    signUpInfoUiModel: SignUpInfoUiModel,
    idDuplicationCheck: Boolean,
    passwordSameCheck: Boolean,
    emailDuplicationCheck: Boolean,
    allEssentialTermsAgree: Boolean
) {
    var signUpEnable by remember {
        mutableStateOf(false)
    }
    signUpEnable = idDuplicationCheck && passwordSameCheck && emailDuplicationCheck && allEssentialTermsAgree
    FMButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(signUpInfoUiModel) },
        enabled = signUpEnable,
        text = stringResource(R.string.sign_up_btn)
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
    list: List<SignUpTermUiModel>,
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
            SignUpTermUiModel(true, R.string.sign_up_service_term_agree, CheckedStatus.UNCHECKED),
            SignUpTermUiModel(true, R.string.sign_up_identification_term_agree, CheckedStatus.UNCHECKED),
            SignUpTermUiModel(false, R.string.sign_up_marketing_alarm_term_agree, CheckedStatus.UNCHECKED)
        )
    }

    Column {
        TermItem(
            imageResources = listOf(R.drawable.circle_uncheck, R.drawable.circle_check),
            description = R.string.sign_up_all_term_agree,
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
    SignUpTextFieldVerticalSpacer()
}


@Composable
fun SignUpTextFieldVerticalSpacer() {
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    FamilyMomentsTheme {
        SignUpScreen(
            SignUpViewModel(
                SignInRepositoryImpl(object : SignInService {
                    override suspend fun checkId(checkIdRequest: CheckIdRequest): CheckIdResponse {
                        return CheckIdResponse(true, 200, "", "")
                    }

                    override suspend fun checkEmail(checkEmailRequest: CheckEmailRequest): CheckEmailResponse {
                        return CheckEmailResponse(true, 200, "", "")
                    }

                    override suspend fun executeSignUp(
                        profileImg: MultipartBody.Part,
                        signUpRequest: SignUpRequest
                    ): SignUpResponse {
                        TODO("Not yet implemented")
                    }
                })
            )
        )
    }
}
