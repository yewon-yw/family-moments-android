package io.familymoments.app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.familymoments.app.R

@Composable
fun SelectImageButton(
    modifier: Modifier = Modifier
) {
    var isMenuExpanded: Boolean by remember { mutableStateOf(false) }
    Button(
        modifier = modifier,
        onClick = { isMenuExpanded = !isMenuExpanded },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF3F4F7),
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
    ) {

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
            ImageSelectDropDownMenu(isMenuExpanded) { isMenuExpanded = false }
        }
    }
}
