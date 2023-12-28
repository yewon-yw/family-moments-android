package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.ui.theme.FamilyMomentsTheme

@Composable
fun JoinScreen() {
    LazyColumn {
        item {
            JoinContentScreen()
        }
    }
}

@Composable
fun JoinContentScreen() {
    Column {
        JoinTextField(title = "아이디", label = "아이디를 입력하세요", button = true, buttonLabel = "중복확인")
        JoinTextFieldVerticalSpacer()
        JoinTextField(title = "비밀번호", label = "비밀번호를 입력하세요")
        JoinTextFieldVerticalSpacer()
        JoinTextField(title = "비밀번호 확인", label = "비밀번호를 한번 더 입력하세요")
        JoinTextFieldVerticalSpacer()
        JoinTextField(title = "이름", label = "실명을 입력하세요 ex)홍길동")
        JoinTextFieldVerticalSpacer()
        JoinTextField(
            title = "이메일 인증",
            label = "성명을 입력하세요 ex)홍길동",
            button = true,
            buttonLabel = "중복확인",
        )
        JoinTextFieldVerticalSpacer()
        JoinTextField(title = "생년월일", label = "8자리 입력 ex) 199990101")
        JoinTextFieldVerticalSpacer()
        JoinTextField(title = "닉네임", label = "3~8자리 입력(특수문자 불가)")
        JoinTextFieldVerticalSpacer()
        Text(text = "프로필 사진 선택", color = Color(0xFF5B6380), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(7.dp))
        Button(
            onClick = { /*TODO*/ },
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3F4F7),
                ),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(115.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_select_pic),
                    contentDescription = "",
                )
                Text(text = "사진 선택", color = Color(0xFFBFBFBF))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "사용하실 프로필 이미지를 선택헤주세요.",
            color = Color(0xFFA9A9A9),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize(
                        Alignment.Center,
                    ),
        )
    }
}

@Composable
fun JoinTextField(
    title: String,
    label: String,
    button: Boolean = false,
    buttonLabel: String = "",
) {
    var input by remember { mutableStateOf(TextFieldValue()) }
    Text(text = title, color = Color(0xFF5B6380), fontWeight = FontWeight.Bold)
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = if (!button) Modifier.fillMaxWidth() else Modifier,
            value = input,
            onValueChange = { input = it },
            label = { Text(text = label) },
            shape = RoundedCornerShape(8.dp),
            colors =
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                ),
        )
        if (button) {
            Spacer(modifier = Modifier.width(7.dp))
            Button(
                onClick = { /*TODO*/ },
                content = { Text(text = buttonLabel, fontSize = 16.sp) },
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF5B6380),
                        contentColor = Color.White,
                    ),
            )
        }
    }
}

@Composable
fun JoinTextFieldVerticalSpacer() {
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewJoinScreen() {
    FamilyMomentsTheme {
        JoinScreen()
    }
}
