package io.familymoments.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.ui.theme.AppColors

@Composable
fun JoinInputField(
        title: String,
        label: String,
        buttonExist: Boolean = false,
        onButtonClick: (TextFieldValue) -> Unit = {},
        onTextFieldChange: (String) -> Unit = {},
        buttonLabel: String = "",
        warningText: String = "",
        checkValidation: (String) -> Unit = {},
        validation: Boolean = false,
) {
    var textFieldValue: TextFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var buttonAvailable: Boolean by remember { mutableStateOf(false) }

    Text(text = title, color = Color(0xFF5B6380), fontWeight = FontWeight.Bold)

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedTextField(
                modifier = if (buttonExist) Modifier.weight(1f)
                else Modifier.fillMaxWidth(),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onTextFieldChange(textFieldValue.text)
                    checkValidation(it.text)
                },
                label = { Text(text = label, fontSize = 16.sp) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                ),
        )
        if (buttonExist) {
            Button(
                    modifier = Modifier
                            .wrapContentWidth()
                            .padding(7.dp),
                    enabled = buttonAvailable,
                    onClick = { onButtonClick(textFieldValue) },
                    content = { Text(text = buttonLabel, fontSize = 16.sp) },
                    colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColors.deepPurple1,
                            contentColor = Color.White,
                    ),
                    contentPadding = PaddingValues(13.dp)
            )
        }
    }
    buttonAvailable = if (!validation) {
        if (textFieldValue.text.isNotEmpty()) Text(text = warningText, color = Color.Red)
        false
    } else {
        true
    }
}