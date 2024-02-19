package io.familymoments.app.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun JoinTextFieldArea(
    modifier:Modifier = Modifier,
    title:String,
    hint: String,
    onValueChange: (TextFieldValue) -> Unit,
    showCheckButton: Boolean = false,
    checkButtonAvailable:Boolean = false,
    onCheckButtonClick: (TextFieldValue) -> Unit = {},
    showDeleteButton: Boolean = false,
    borderColor: Color = AppColors.grey2,
    textColor:Color = AppColors.grey2
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    Column {

        Text(
            text = title,
            color = AppColors.deepPurple1,
            style = AppTypography.SH3_16
        )
        Spacer(modifier = Modifier.height(7.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FMTextField(
                modifier = modifier.then(if (showCheckButton) Modifier.weight(1f)
                else Modifier.fillMaxWidth()),
                onValueChange = {
                    textFieldValue = it
                    onValueChange(textFieldValue)
                },
                value = textFieldValue,
                hint = hint,
                showDeleteButton = showDeleteButton,
                borderColor = borderColor,
                textColor = textColor
            )
            if (showCheckButton) {
                CheckButton(checkButtonAvailable,textFieldValue, onCheckButtonClick)
            }
        }
    }
}

@Composable
private fun CheckButton(checkButtonAvailable: Boolean, textFieldValue:TextFieldValue, onCheckButtonClick: (TextFieldValue) -> Unit){
    Button(
        modifier = Modifier
            .wrapContentWidth()
            .padding(start = 7.dp),
        enabled = textFieldValue.text.isNotEmpty() && checkButtonAvailable,
        onClick = { onCheckButtonClick(textFieldValue) },
        content = { Text(text = "중복확인", fontSize = 16.sp) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.deepPurple1,
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(13.dp)
    )
}


@Composable
fun ShowWarningText(
    text:String,
    textFieldValue: TextFieldValue,
    validation:Boolean,
    colorChange:(Color)->Unit){

    if (textFieldValue.text.isNotEmpty() && !validation) {
        colorChange(AppColors.red1)
        Text(
            text = text,
            color = AppColors.red2,
            style = AppTypography.LB2_11
        )
    }
    if (textFieldValue.text.isEmpty() || validation) {
        colorChange(AppColors.grey2)
    }
}

@Preview
@Composable
fun PreviewJoinTextField() {
    JoinTextFieldArea(title = "",hint = "", onValueChange = {})
}
