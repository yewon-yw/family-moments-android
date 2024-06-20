package io.familymoments.app.feature.removemember.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.network.dto.response.Member
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.noRippleClickable

@Composable
fun RemoveFamilyMemberScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {}
) {
    RemoveFamilyMemberScreenUI(
        modifier = modifier,
    )
}

@Composable
fun RemoveFamilyMemberScreenUI(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 55.dp, bottom = 33.dp),
            text = stringResource(id = R.string.remove_family_member_title),
            style = AppTypography.SH2_18,
            color = AppColors.deepPurple1
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(10) {
//                FamilyMember()
//                HorizontalDivider(thickness = 1.dp, color = AppColors.grey3)
            }
        }
        FMButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 29.dp, bottom = 95.dp)
                .height(59.dp),
            onClick = { /*TODO*/ },
            text = stringResource(id = R.string.remove_family_member_btn),
            containerColor = AppColors.deepPurple1
        )
    }
}

@Composable
fun FamilyMember(
    member: Member,
    onSelectedMemberChanged: (Member?) -> Unit,
    selectedMember: Member?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .noRippleClickable {
                onSelectedMemberChanged(
                    if (selectedMember == member) null else member
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = member.profileImg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 15.dp)
                .clip(CircleShape)
                .size(48.dp)
        )
        Text(text = member.id, style = AppTypography.B2_14, color = AppColors.black1)
        Box(
            modifier = Modifier
                .weight(1f)
                .size(28.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = if (selectedMember == member) R.drawable.ic_round_checked else R.drawable.ic_round_unchecked),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RemoveFamilyMemberScreenPreview() {
    RemoveFamilyMemberScreenUI()
}
