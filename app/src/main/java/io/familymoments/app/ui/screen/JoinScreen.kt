package io.familymoments.app.ui.screen

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    var id: String
    var password: String by remember { mutableStateOf("") }
    var name: String
    var email: String
    var birthDay: String
    var nickname: String
    var bitmap: Bitmap
    var allAgree: CheckedStatus by remember { mutableStateOf(CheckedStatus.UNCHECKED) }

    Column {
        IdField(viewModel = viewModel) { id = it }
        PasswordField(viewModel = viewModel) { password = it }
        PasswordCheckField(password = password)
        NameField { name = it }
        EmailField { email = it }
        BirthDayField { birthDay = it }
        NicknameField { nickname = it }
        ProfileImageField { bitmap = it }
        Spacer(modifier = Modifier.height(53.dp))
        TermsField { allAgree = it }
        StartButtonField()
    }
}


@Composable
fun IdField(viewModel: JoinViewModel, onTextFieldChange: (String) -> Unit) {
    val userIdValidation = viewModel.userIdValidation.collectAsState()
    JoinInputField(title = stringResource(R.string.join_id_field_title),
            label = stringResource(R.string.join_id_field_label),
            buttonExist = true,
            buttonLabel = stringResource(R.string.join_check_duplication_btn),
            onButtonClick = { viewModel.checkIdDuplicate(it.text) },
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
fun PasswordCheckField(password: String) {
    var currentPassword: String by remember { mutableStateOf("") }
    JoinInputField(
            title = stringResource(R.string.join_password_check_field_title),
            label = stringResource(R.string.join_password_check_field_label),
            warningText = stringResource(R.string.join_password_check_validation_warning),
            onTextFieldChange = { currentPassword = it },
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
fun EmailField(onTextFieldChange: (String) -> Unit) {
    JoinInputField(title = stringResource(R.string.join_email_field_title),
            label = stringResource(R.string.join_email_field_label),
            buttonExist = true,
            buttonLabel = stringResource(R.string.join_check_duplication_btn),
            onTextFieldChange = { onTextFieldChange(it) })
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
fun ProfileImageField(onBitmapChange: (Bitmap) -> Unit) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isMenuExpanded: Boolean by remember { mutableStateOf(false) }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    Text(text = "프로필 사진 선택", color = Color(0xFF5B6380), fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(7.dp))
    Button(
            onClick = { isMenuExpanded = !isMenuExpanded },
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3F4F7),
            ),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
    ) {
        // 갤러리에서 선택
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                onBitmapChange(btm)
                Image(bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp))
            }
        }

        val launcher = rememberLauncherForActivityResult(
                contract =
                ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DropdownMenu(expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }) {
                DropdownMenuItem(onClick = {
                    launcher.launch("image/*")
                    isMenuExpanded = false
                }) {
                    Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_gallery))
                }
                DropdownMenuItem(onClick = {
                    imageUri = Uri.Builder()
                            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                            .authority(context.resources.getResourcePackageName(R.drawable.default_profile)).appendPath(context.resources.getResourceTypeName(R.drawable.default_profile)).appendPath(context.resources.getResourceEntryName(R.drawable.default_profile))
                            .build()
                    isMenuExpanded = false
                }) {
                    Text(text = stringResource(R.string.join_select_profile_image_drop_down_menu_default_image))
                }
            }
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
fun StartButtonField() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
                enabled = false,
                onClick = { },
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
fun TermsField(onAllCheckedChange: (CheckedStatus) -> Unit) {

    var allEssentialChecked by remember { mutableStateOf(CheckedStatus.UNCHECKED) }
    var allAgreeChecked by remember { mutableStateOf(CheckedStatus.UNCHECKED) }
    val terms = listOf(
            JoinTerm(true, R.string.join_service_term_agree, CheckedStatus.UNCHECKED),
            JoinTerm(true, R.string.join_identification_term_agree, CheckedStatus.UNCHECKED),
            JoinTerm(false, R.string.join_marketing_alarm_term_agree, CheckedStatus.UNCHECKED)
    )

    Column {
        Row {
            CheckBox(
                    imageResources = listOf(R.drawable.circle_uncheck, R.drawable.circle_check),
                    defaultStatus = allAgreeChecked,
                    onCheckedChange = {
                        allAgreeChecked = it
                        onAllCheckedChange(allAgreeChecked)
                    }
            )

            Text(
                    modifier = Modifier.padding(start = 14.dp),
                    text = stringResource(R.string.join_all_term_agree),
                    fontSize = 16.sp
            )
        }
        Divider(modifier = Modifier.padding(vertical = 11.dp), thickness = 1.dp, color = AppColors.grey2)
        for (term in terms) {
            Row {
                CheckBox(
                        imageResources = listOf(R.drawable.uncheck, R.drawable.check),
                        defaultStatus = allAgreeChecked,
                        onCheckedChange = {
                            term.checkedStatus = it
                            allAgreeChecked = if (terms.all { it.checkedStatus == CheckedStatus.CHECKED }) {
                                CheckedStatus.CHECKED
                            } else {
                                CheckedStatus.UNCHECKED
                            }
                        }
                )

                Text(
                        modifier = Modifier.padding(start = 14.dp),
                        text = stringResource(id = term.description),
                        fontSize = 13.sp
                )
            }
        }
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

//@Preview(showBackground = true)
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
