package io.familymoments.app.feature.transferpermission.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.choosingfamily.component.MemberCheckBox

@Composable
fun TransferPermissionScreen(modifier: Modifier) {
    TransferPermissionScreenUI(modifier)
}

@Composable
fun TransferPermissionScreenUI(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 55.dp, bottom = 33.dp),
            text = stringResource(id = R.string.transfer_family_permission_title),
            style = AppTypography.SH2_18,
            color = AppColors.deepPurple1
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(10) {
                FamilyMember()
                HorizontalDivider(thickness = 1.dp, color = AppColors.grey3)
            }
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 29.dp, bottom = 95.dp)
                .height(59.dp),
            onClick = { /*TODO*/ },
            text = stringResource(id = R.string.transfer_family_permission_btn),
            containerColor = AppColors.deepPurple1
        )
    }
}

@Composable
fun FamilyMember() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_profile_test),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 15.dp)
                .clip(CircleShape)
                .size(48.dp)
        )
        Text(text = "member", style = AppTypography.B2_14, color = AppColors.black1)
        MemberCheckBox(
            modifier = Modifier
                .weight(1f)
                .size(28.dp),
            onCheckChanged = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransferPermissionScreenPreview() {
    TransferPermissionScreenUI()
}
