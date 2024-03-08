package io.familymoments.app.feature.joiningfamily.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.choosingfamily.ChoosingFamilyHeaderButtonLayout
import io.familymoments.app.feature.choosingfamily.MemberCheckBox
import io.familymoments.app.feature.choosingfamily.SearchTextField


@Composable
fun JoinScreen(navigate: () -> Unit = {}) {
    var idTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    ChoosingFamilyHeaderButtonLayout(
        headerBottomPadding = 18.dp,
        header = stringResource(R.string.header_join_family),
        button = stringResource(R.string.button_family_join_now),
        onClick = navigate
    ) {
        Column {
            SearchTextField(
                hint = stringResource(R.string.family_invitation_link_text_field_hint)
            ) { idTextFieldValue = it }
            Spacer(modifier = Modifier.height(36.dp))
            Box(modifier = Modifier.background(color = AppColors.grey6)) {
                FamilyProfile(resourceId = R.drawable.sample_member_image, name = "가족 이름")
            }
        }

    }
}

@Composable
private fun FamilyProfile(resourceId: Int, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(end = 15.dp)
                .clip(CircleShape)
                .size(48.dp),
            painter = painterResource(id = resourceId),
            contentDescription = null
        )
        Text(text = name, style = AppTypography.B2_14, color = Color(0xFF1B1A57))
        MemberCheckBox(modifier = Modifier
            .weight(1f)
            .size(28.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJoinScreen() {
    JoinScreen()
}