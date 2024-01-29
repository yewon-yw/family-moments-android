package io.familymoments.app.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

@Composable
fun FMTextField(
    onValueChange: (TextFieldValue) -> Unit,
    value: TextFieldValue,
    hint: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(width = 2.dp, color = AppColors.grey2, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                onValueChange = onValueChange,
                value = value,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                decorationBox = { innerTextField ->
                    if (value.text.isEmpty()) {
                        Text(text = hint, style = AppTypography.BTN5_16, color = AppColors.grey2)
                    }
                    innerTextField()
                },
                modifier = Modifier.clip(RoundedCornerShape(4.dp))
            )

            if (value.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { onValueChange(TextFieldValue("")) }
                )
            }

        }
    }
}
