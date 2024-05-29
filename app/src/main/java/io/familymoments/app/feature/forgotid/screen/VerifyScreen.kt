package io.familymoments.app.feature.forgotid.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun VerifyScreen(navigate: () -> Unit) {
    var name by remember {
        mutableStateOf(TextFieldValue())
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(83.dp))
        Text(
            text = "아이디 찾기",
            style = AppTypography.H1_36,
            color = AppColors.deepPurple1
        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = "본인 확인을 위하여 이메일로 인증해주세요.",
            style = AppTypography.LB1_13,
            color = AppColors.grey2
        )
        Spacer(modifier = Modifier.height(67.dp))
        Text(
            text = "이름",
            style = AppTypography.SH2_18,
            color = AppColors.grey2
        )
        Spacer(modifier = Modifier.height(17.dp))
        FMTextField(
            onValueChange = { name = it },
            value = name,
            hint = "실명을 입력하세요 ex) 홍길동",
            showDeleteButton = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "이메일",
            style = AppTypography.SH2_18,
            color = AppColors.grey2
        )
        Spacer(modifier = Modifier.height(17.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FMTextField(
                modifier = Modifier
                    .weight(1f),
                onValueChange = { name = it },
                value = name,
                hint = "example@email.com"
            )
            Spacer(modifier = Modifier.width(7.dp))
            FMButton(
                onClick = { },
                text = "인증받기",
                radius = 10.dp,
                textStyle = AppTypography.BTN5_16,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        FMTextField(onValueChange = { name = it }, value = name, hint = "인증번호 6자리", showDeleteButton = true)
        Spacer(modifier = Modifier.height(33.dp))
        FMButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = "다음",
            contentPaddingValues = PaddingValues(top = 20.dp, bottom = 19.dp)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun VerifyScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        VerifyScreen {}
    }

}
