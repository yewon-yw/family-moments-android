package io.familymoments.app.feature.deleteaccount.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun DeleteAccountPopup(onDismissRequest:()->Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = AppColors.grey6)
        ) {
            Image(
                modifier = Modifier
                    .clickable { }
                    .align(Alignment.TopEnd)
                    .padding(top = 14.dp, end = 14.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_album_popup_close),
                contentDescription = "close popup",
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 42.dp, bottom = 30.dp),
                    text = stringResource(R.string.account_delete_popup_label),
                    style = AppTypography.BTN4_18,
                    color = AppColors.deepPurple1
                )
                FMButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 38.dp, end = 38.dp, bottom = 21.dp),
                    onClick = onDismissRequest,
                    text = stringResource(id = R.string.complete_pop_up_btn_ok),
                    containerColor = AppColors.purple2,
                    contentPaddingValues = PaddingValues(vertical = 16.dp)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteAccountPopupPreview() {
    DeleteAccountPopup()
}
