@file:OptIn(ExperimentalMaterial3Api::class)

package io.familymoments.app.feature.familyinvitationlink.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.familyinvitationlink.viewmodel.FamilyInvitationLinkViewModel

@Composable
fun FamilyInvitationLinkScreen(
    modifier: Modifier = Modifier,
    viewModel: FamilyInvitationLinkViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getFamilyInvitationLink()
    }
    FamilyInvitationLinkScreenUI(modifier = modifier, invitationCode = uiState.value.invitationLink)
}

private fun copyInvitationCode(context: Context, invitationLink: String) {
    val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clipData: ClipData = ClipData.newPlainText("invitationLink", invitationLink)
    clipboardManager.setPrimaryClip(clipData)
    // Android 12 버전 이하에서만 토스트 메시지 출력
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Toast.makeText(context, R.string.family_invitation_link_copied, Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun FamilyInvitationLinkScreenUI(
    modifier: Modifier = Modifier,
    invitationCode: String
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.family_setting_title),
                style = AppTypography.B1_16,
                color = AppColors.black1
            )
        }
        Text(
            modifier = Modifier.padding(top = 40.dp, bottom = 16.dp),
            text = stringResource(id = R.string.family_invitation_code_title),
            style = AppTypography.SH2_18,
            color = AppColors.grey8
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.5.dp, color = AppColors.grey2, shape = RoundedCornerShape(8.dp))
                .background(color = AppColors.pink5)
                .padding(vertical = 12.dp, horizontal = 11.dp)
                .clickable {
                    copyInvitationCode(context, invitationCode)
                },
        ) {
            Row {
                BasicTextField(
                    value = invitationCode,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    textStyle = AppTypography.SH2_18.copy(AppColors.grey2),
                    modifier = Modifier.weight(1f),
                ) { innerTextField ->
                    innerTextField()
                }
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.copy),
                    contentDescription = null,
                    Modifier.padding(end = 8.dp)
                )
            }
        }
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "FamilyMoments 가족에 당신을 초대합니다.\n" +
                    "아래 링크를 통해 앱을 설치하거나 가족에 참여하세요!\n\n" +
                    "참여링크: https://familymoments.github.io/web-pages?code=$invitationCode" +
                    "")
                context.startActivity(Intent.createChooser(intent, "초대 링크 공유하기"))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 59.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.purple2)
        ) {
            Text(
                text = stringResource(id = R.string.family_invitation_link_share),
                style = AppTypography.BTN4_18,
                color = AppColors.grey6
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyInvitationLinkScreenPreview() {
    FamilyInvitationLinkScreenUI(invitationCode = "https://")
}
