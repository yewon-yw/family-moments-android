package io.familymoments.app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

@Composable
fun FMTextField(
    text: String,
    placeholder: String,
    onTextChanged: (String) -> Unit = {},
) {
    var value by remember { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                value = it
                onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color = if (isFocused) AppColors.purple2 else AppColors.grey2,
                    shape = RoundedCornerShape(8.dp)
                )
                .size(46.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 11.dp, top = 15.dp, bottom = 11.dp, end = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = AppTypography.BTN5_16,
                        color = AppColors.grey2,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Text(
                        text = value,
                        style = AppTypography.BTN5_16,
                        color = AppColors.black2,
                        modifier = Modifier.weight(1f)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_text_field_clear),
                    contentDescription = "Clear Text",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            value = ""
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FMTextFieldPreview() {
    FMTextField(text = "홍길동", placeholder = "이름을 입력하세요.", onTextChanged = {})
}
