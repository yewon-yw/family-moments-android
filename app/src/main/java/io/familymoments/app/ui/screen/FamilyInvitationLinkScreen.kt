package io.familymoments.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

@Composable
fun FamilyInvitationLinkScreen() {
    CreateFamilyLayoutSkeleton(
        headerBottomPadding = 16.dp,
        header = stringResource(R.string.family_invitation_link_header),
        button = stringResource(R.string.next_btn)
    ) {
        Column {
            LinkTextField()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                onClick = { },
                shape = RoundedCornerShape(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.purple2),
                contentPadding = PaddingValues(vertical = 8.8.dp)
            ) {
                Text(text = stringResource(R.string.copy_invitation_link_btn))
            }
        }


    }
}

@Composable
fun LinkTextField() {
    var invitationLink by remember {
        mutableStateOf(TextFieldValue())
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.5.dp, color = AppColors.grey2, shape = RoundedCornerShape(8.dp))
            .background(AppColors.pink6, shape = RoundedCornerShape(7.dp))
            .padding(vertical = 12.dp, horizontal = 11.dp),
    ) {
        BasicTextField(value = invitationLink, onValueChange = { invitationLink = it }) {
            Text(
                text = stringResource(R.string.family_name_text_field_hint),
                style = AppTypography.SH2_18,
                color = AppColors.grey2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFamilyInvitationLinkScreen() {
    FamilyInvitationLinkScreen()
}
