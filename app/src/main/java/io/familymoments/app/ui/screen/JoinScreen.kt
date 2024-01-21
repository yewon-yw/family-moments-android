package io.familymoments.app.ui.screen

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.model.join.data.request.CheckEmailRequest
import io.familymoments.app.model.join.data.request.CheckIdRequest
import io.familymoments.app.model.join.data.request.JoinRequest
import io.familymoments.app.model.join.data.response.CheckEmailResponse
import io.familymoments.app.model.join.data.response.CheckIdResponse
import io.familymoments.app.model.join.data.response.JoinResponse
import io.familymoments.app.model.join.ui.JoinInfoUiModel
import io.familymoments.app.model.join.ui.JoinTermUiModel
import io.familymoments.app.network.JoinService
import io.familymoments.app.repository.impl.JoinRepositoryImpl
import io.familymoments.app.ui.component.AppBarScreen
import io.familymoments.app.ui.component.CheckBox
import io.familymoments.app.ui.component.CheckedStatus
import io.familymoments.app.ui.component.JoinInputField
import io.familymoments.app.ui.component.LoadingIndicator
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.JoinViewModel
import okhttp3.MultipartBody

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JoinScreen(viewModel: JoinViewModel) {
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.join_activity_app_bar_title),
                color = AppColors.deepPurple1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back_btn),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    ) {
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
}


@Composable
fun JoinContentScreen(viewModel: JoinViewModel) {
    val context = LocalContext.current
    var password: String by remember { mutableStateOf("") }
    val bitmap: Bitmap = getDefaultProfileImageBitmap(context)
    var allEssentialTermsAgree by remember {
        mutableStateOf(false)
    }
    var userIdDuplicationCheck by remember { mutableStateOf(false) }
    var emailDuplicationCheck by remember { mutableStateOf(false) }

    viewModel.isLoading.collectAsState().value.let {
        LoadingIndicator(isLoading = it)
    }

    viewModel.userIdDuplicationCheck.collectAsState().value.let {
        userIdDuplicationCheck = it ?: false
        if (it == true) {
            Toast.makeText(
                context,
                context.getText(R.string.join_check_user_id_duplication_success),
                Toast.LENGTH_SHORT
            ).show()
        } else if (it == false) {
            Toast.makeText(context, context.getText(R.string.join_check_user_id_duplication_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

    viewModel.emailDuplicationCheck.collectAsState().value.let {
        emailDuplicationCheck = it ?: false
        if (it == true) {
            Toast.makeText(context, context.getText(R.string.join_check_email_duplication_success), Toast.LENGTH_SHORT)
                .show()
        } else if (it == false) {
            Toast.makeText(context, context.getText(R.string.join_check_email_duplication_fail), Toast.LENGTH_SHORT)
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
        PasswordField(viewModel = viewModel) {
            password = it
            joinInfoUiModel = joinInfoUiModel.copy(password = it)
        }
        PasswordCheckField(password = password) { passwordSameCheck = it }
        NameField { joinInfoUiModel = joinInfoUiModel.copy(name = it) }
        EmailField(viewModel) { joinInfoUiModel = joinInfoUiModel.copy(email = it) }
        BirthDayField { joinInfoUiModel = joinInfoUiModel.copy(birthDay = it) }
        NicknameField { joinInfoUiModel = joinInfoUiModel.copy(nickname = it) }
        ProfileImageField {
            joinInfoUiModel = joinInfoUiModel.copy(bitmap = it)
        }
        Spacer(modifier = Modifier.height(53.dp))
        TermsField { allEssentialTermsAgree = it }
        StartButtonField(
            viewModel,
            joinInfoUiModel,
            userIdDuplicationCheck,
            passwordSameCheck,
            emailDuplicationCheck,
            allEssentialTermsAgree
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun IdField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val userIdValidation = viewModel.userIdValidation.collectAsState()
    JoinInputField(title = stringResource(R.string.join_id_field_title),
        label = stringResource(R.string.join_id_field_label),
        buttonExist = true,
        buttonLabel = stringResource(R.string.join_check_duplication_btn),
        onButtonClick = {
            viewModel.checkIdDuplication(it.text)
        },
        onTextFieldChange = {
            onTextFieldChange(it)
            viewModel.checkIdValidation(it)
        },
        warningText = stringResource(R.string.join_id_validation_warning),
        validation = userIdValidation.value,
        checkValidation = { }
    )
    JoinTextFieldVerticalSpacer()
}

@Composable
fun PasswordField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val passwordValidation = viewModel.passwordValidation.collectAsState()
    JoinInputField(
        title = stringResource(R.string.join_password_field_title),
        label = stringResource(R.string.join_password_field_label),
        warningText = stringResource(R.string.join_password_validation_warning),
        onTextFieldChange = {
            onTextFieldChange(it)
            viewModel.checkPasswordValidation(it)
        },
        validation = passwordValidation.value
    )
    JoinTextFieldVerticalSpacer()
}

@Composable
fun PasswordCheckField(password: String, passwordSameCheck: (Boolean) -> Unit) {
    var currentPassword: String by remember { mutableStateOf("") }
    JoinInputField(
        title = stringResource(R.string.join_password_check_field_title),
        label = stringResource(R.string.join_password_check_field_label),
        warningText = stringResource(R.string.join_password_check_validation_warning),
        onTextFieldChange = {
            currentPassword = it
            passwordSameCheck(currentPassword == password)
        },
        validation = currentPassword == password
    )
    JoinTextFieldVerticalSpacer()
}

@Composable
fun NameField(onTextFieldChange: (String) -> Unit) {
    JoinInputField(
        title = stringResource(R.string.join_name_field_title),
        label = stringResource(R.string.join_name_field_label),
        onTextFieldChange = { onTextFieldChange(it) })
    JoinTextFieldVerticalSpacer()
}

@Composable
fun EmailField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val emailValidation = viewModel.emailValidation.collectAsState()
    JoinInputField(
        title = stringResource(R.string.join_email_field_title),
        label = stringResource(R.string.join_email_field_label),
        buttonExist = true,
        onButtonClick = { viewModel.checkEmailDuplication(it.text) },
        buttonLabel = stringResource(R.string.join_check_duplication_btn),
        onTextFieldChange = {
            onTextFieldChange(it)
            viewModel.checkEmailValidation(it)
        },
        warningText = stringResource(R.string.join_email_validation_warning),
        validation = emailValidation.value
    )
    JoinTextFieldVerticalSpacer()
}

@Composable
fun BirthDayField(onTextFieldChange: (String) -> Unit) {
    JoinInputField(title = stringResource(R.string.join_birthday_field_title),
        label = stringResource(R.string.join_birthday_field_label),
        onTextFieldChange = { onTextFieldChange(it) })
    JoinTextFieldVerticalSpacer()
}

@Composable
fun NicknameField(onTextFieldChange: (String) -> Unit) {
    JoinInputField(
        title = stringResource(R.string.join_nickname_field_title),
        label = stringResource(R.string.join_nickname_field_label),
        onTextFieldChange = { onTextFieldChange(it) })
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
    joinViewModel: JoinViewModel,
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
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            enabled = joinEnable,
            onClick = { joinViewModel.join(joinInfoUiModel) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.deepPurple1, contentColor = Color.White
            ),
            modifier = Modifier.align(alignment = Alignment.Center),
            shape = RoundedCornerShape(60.dp),
        ) {

            Text(
                text = stringResource(R.string.join_start_btn),
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 40.dp),
                fontSize = 18.sp
            )
        }
    }
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
        CheckBox(
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
        JoinScreen(JoinViewModel(JoinRepositoryImpl(object : JoinService {
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
