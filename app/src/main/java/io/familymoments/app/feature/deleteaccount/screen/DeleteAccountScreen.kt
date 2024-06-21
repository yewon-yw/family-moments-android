package io.familymoments.app.feature.deleteaccount.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun DeleteAccountScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 13.dp)) {
        Spacer(modifier = Modifier.height(18.dp))
        DeleteAccountHeader()
        Spacer(modifier = Modifier.height(141.dp))
        DeleteAccountContent()
    }

}

@Composable
private fun DeleteAccountHeader() {
    Text(text = stringResource(id = R.string.my_page_label_account_deletion), style = AppTypography.B1_16, color = AppColors.black1)
}

@Composable
private fun DeleteAccountContent() {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = stringResource(R.string.account_deletion_notice_text),
            style = AppTypography.BTN4_18, color = AppColors.deepPurple1
        )
        Spacer(modifier = Modifier.height(58.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.account_deletion_farewell_text),
            style = AppTypography.BTN3_20, color = AppColors.deepPurple1,
            textAlign = TextAlign.Center, lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.height(103.dp))
        FMButton(
            onClick = { /*TODO 팝업 띄우기 */ },
            text = stringResource(R.string.account_deletion_delete_btn),
            containerColor = AppColors.pink1,
            modifier = Modifier.fillMaxWidth(),
            contentPaddingValues = PaddingValues(vertical = 18.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        FMButton(
            onClick = { /*TODO 뒤로 가기*/ },
            text = stringResource(R.string.account_deletion_cancel_btn),
            containerColor = AppColors.deepPurple1,
            modifier = Modifier.fillMaxWidth(),
            contentPaddingValues = PaddingValues(vertical = 18.dp)
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun DeleteAccountScreenPreview() {
    DeleteAccountScreen()
}
