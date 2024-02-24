package io.familymoments.app.core.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun FMTextField(
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    value: TextFieldValue,
    hint: String,
    showBorder: Boolean = true,
    borderColor: Color = AppColors.grey2,
    showDeleteButton:Boolean = true,
    textColor:Color = AppColors.grey2,
) {
    Box(
        modifier = modifier
            .then(
                if (showBorder) {
                    Modifier.border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                onValueChange = onValueChange,
                value = value,
                singleLine = true,
                textStyle = AppTypography.BTN5_16,
                decorationBox = { innerTextField ->
                    if (value.text.isEmpty()) {
                        Text(text = hint, style = AppTypography.BTN5_16, color = textColor)
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .weight(1f)
            )

            if (showDeleteButton && value.text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_text_field_delete),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onValueChange(TextFieldValue("")) }
                )
            }

        }
    }
}

@Preview
@Composable
fun FMTextFieldPreview() {
    FMTextField(
        onValueChange = {},
        value = TextFieldValue("asd"),
        hint = "Hint"
    )
}