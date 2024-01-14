package io.familymoments.app.ui.screen

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.model.CheckEmailRequest
import io.familymoments.app.model.CheckEmailResponse
import io.familymoments.app.model.CheckIdRequest
import io.familymoments.app.model.CheckIdResponse
import io.familymoments.app.model.JoinInfoUiModel
import io.familymoments.app.model.JoinRequest
import io.familymoments.app.model.JoinResponse
import io.familymoments.app.model.JoinTerm
import io.familymoments.app.network.JoinService
import io.familymoments.app.repository.impl.JoinRepositoryImpl
import io.familymoments.app.ui.component.CheckBox
import io.familymoments.app.ui.component.CheckedStatus
import io.familymoments.app.ui.component.JoinInputField
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.JoinViewModel
import okhttp3.MultipartBody

@Composable
fun JoinScreen(viewModel: JoinViewModel) {
    LazyColumn(modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)) {
        item {
            JoinContentScreen(viewModel)
        }
    }
}


@Composable
fun JoinContentScreen(viewModel: JoinViewModel) {
    val context = LocalContext.current
    var password: String by remember { mutableStateOf("") }
    val bitmap: Bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.default_profile)
    var allEssentialTermsAgree by remember {
        mutableStateOf(false)
    }
    var userIdDuplicationCheck by remember { mutableStateOf(false) }
    var emailDuplicationCheck by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        viewModel.userIdDuplicationCheck.collect {
            userIdDuplicationCheck = it ?: false
            if (it == true) {
                Toast.makeText(context, context.getText(R.string.join_check_user_id_duplication_success), Toast.LENGTH_SHORT).show()
            } else if (it == false) {
                Toast.makeText(context, context.getText(R.string.join_check_user_id_duplication_fail), Toast.LENGTH_SHORT).show()
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.emailDuplicationCheck.collect {
            emailDuplicationCheck = it ?: false
            if (it == true) {
                Toast.makeText(context, context.getText(R.string.join_check_email_duplication_success), Toast.LENGTH_SHORT).show()
            } else if (it == false) {
                Toast.makeText(context, context.getText(R.string.join_check_email_duplication_fail), Toast.LENGTH_SHORT).show()
            }
        }
    }

    var passwordSameCheck by remember {
        mutableStateOf(false)
    }
    var joinInfoUiModel: JoinInfoUiModel by remember {
        mutableStateOf(
                JoinInfoUiModel(
                        id = "id",
                        name = "name",
                        password = "password",
                        email = "email",
                        birthDay = "birthDay",
                        nickname = "nickname",
                        bitmap = bitmap
                )
        )
    }

    Column {
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
        ProfileImageField { joinInfoUiModel = joinInfoUiModel.copy(bitmap = it) }
        Spacer(modifier = Modifier.height(53.dp))
        TermsField { allEssentialTermsAgree = it }
        StartButtonField(viewModel, joinInfoUiModel, userIdDuplicationCheck, passwordSameCheck, emailDuplicationCheck, allEssentialTermsAgree)
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
                viewModel.checkIdDuplicate(it.text)
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
    JoinInputField(title = stringResource(R.string.join_name_field_title), label = stringResource(R.string.join_name_field_label), onTextFieldChange = { onTextFieldChange(it) })
    JoinTextFieldVerticalSpacer()
}

@Composable
fun EmailField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val emailValidation = viewModel.emailValidation.collectAsState()
    JoinInputField(title = stringResource(R.string.join_email_field_title),
            label = stringResource(R.string.join_email_field_label),
            buttonExist = true,
            onButtonClick = { viewModel.checkEmailDuplicate(it.text) },
            buttonLabel = stringResource(R.string.join_check_duplication_btn),
            onTextFieldChange = {
                onTextFieldChange(it)
                viewModel.checkEmailValidation(it)
            },
            warningText = stringResource(R.string.join_email_validation_warning),
            validation = emailValidation.value)
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
fun ProfileImageDropDownMenu(isMenuExpanded: Boolean, isMenuExpandedChanged: (Boolean) -> Unit, getImageUri: (Uri?) -> Unit) {
    val context = LocalContext.current
    var imageUri: Uri?
    val launcher = rememberLauncherForActivityResult(
            contract =
            ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        getImageUri(imageUri)
    }
    DropdownMenu(expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpandedChanged(false) }) {
        DropdownMenuItem(onClick = {
            // 갤러리에서 선택
            launcher.launch("image/*")
            isMenuExpandedChanged(false)
        }) {
            Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_gallery))
        }
        DropdownMenuItem(onClick = {
            // 기본 이미지
            imageUri = Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(context.resources.getResourcePackageName(R.drawable.default_profile)).appendPath(context.resources.getResourceTypeName(R.drawable.default_profile)).appendPath(context.resources.getResourceEntryName(R.drawable.default_profile))
                    .build()
            getImageUri(imageUri)
            isMenuExpandedChanged(false)
        }) {
            Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_default_image))
        }
    }
}

fun convertToBitmap(context: Context, uri: Uri?, onBitmapChange: (Bitmap) -> Unit) {
    val bitmap: Bitmap
    uri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                    .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
        onBitmapChange(bitmap)
    }
}

@Composable
fun ProfileImageField(onBitmapChange: (Bitmap) -> Unit) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isMenuExpanded: Boolean by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Text(text = stringResource(R.string.join_select_profile_image_title), color = Color(0xFF5B6380), fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(7.dp))
    Button(
            modifier = Modifier.height(150.dp),
            onClick = { isMenuExpanded = !isMenuExpanded },
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3F4F7),
            ),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
    ) {
        ProfileImageDropDownMenu(isMenuExpanded, { isMenuExpanded = it }) { imageUri = it }
        // imageUri -> Bitmap 변경
        convertToBitmap(context, imageUri) {
            bitmap = it
            onBitmapChange(it)
        }
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp))
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
                    contentDescription = "",
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
fun StartButtonField(joinViewModel: JoinViewModel, joinInfoUiModel: JoinInfoUiModel, idDuplicationCheck: Boolean, passwordSameCheck: Boolean, emailDuplicationCheck: Boolean, allEssentialTermsAgree: Boolean) {
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
fun TermItem(description: Int, checked: CheckedStatus, onCheckedChange: (CheckedStatus) -> Unit) {
    Row {
        CheckBox(
                imageResources = listOf(R.drawable.uncheck, R.drawable.check),
                defaultStatus = checked,
                onCheckedChange = onCheckedChange
        )
        Text(
                modifier = Modifier.padding(start = 14.dp),
                text = stringResource(id = description),
                fontSize = 13.sp
        )
    }
}

@Composable
fun TermsList(list: List<JoinTerm>, onTermCheckedChange: (Int, CheckedStatus) -> Unit, onTermsCheckedChange: (Boolean) -> Unit) {
    onTermsCheckedChange(list.filter { it.isEssential }.all { it.checkedStatus == CheckedStatus.CHECKED })
    for (index in list.indices) {
        TermItem(
                description = list[index].description,
                checked = list[index].checkedStatus,
                onCheckedChange = { onTermCheckedChange(index, it) })
    }
}

@Composable
fun TermsField(onAllEssentialTermsAgree: (Boolean) -> Unit) {

    val terms = remember {
        mutableStateListOf(
                JoinTerm(true, R.string.join_service_term_agree, CheckedStatus.UNCHECKED),
                JoinTerm(true, R.string.join_identification_term_agree, CheckedStatus.UNCHECKED),
                JoinTerm(false, R.string.join_marketing_alarm_term_agree, CheckedStatus.UNCHECKED)
        )
    }

    Column {
        Row {
            CheckBox(
                    imageResources = listOf(R.drawable.circle_uncheck, R.drawable.circle_check),
                    onCheckedChange = {
                        for (index in terms.indices) {
                            terms[index] = terms[index].copy(checkedStatus = it)
                        }
                    },
                    defaultStatus = if (terms.all { it.checkedStatus == CheckedStatus.CHECKED }) CheckedStatus.CHECKED else CheckedStatus.UNCHECKED
            )
            Text(
                    modifier = Modifier.padding(start = 14.dp),
                    text = stringResource(R.string.join_all_term_agree),
                    fontSize = 16.sp
            )
        }
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
fun PreviewTerms() {
    FamilyMomentsTheme {
        TermsField {}
    }
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
