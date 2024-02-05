package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography
import io.familymoments.app.ui.theme.FamilyMomentsTheme

@Composable
fun SearchMemberScreen() {

    CreateFamilyLayoutSkeleton(
        headerBottomPadding = 34.dp,
        header = stringResource(id = R.string.select_create_family_header),
        button = stringResource(id = R.string.next_btn_one_third)) {
        Column {
            SearchMemberTextField()
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                MemberList()
            }
            Spacer(modifier = Modifier.height(29.dp))
        }
    }


}

@Composable
fun SearchMemberTextField() {
    var idTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    Box(
        modifier = Modifier
            .padding(bottom = 14.dp)
            .border(width = 1.5.dp, color = AppColors.grey2, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 10.dp)
    ) {
        Row {
            Icon(
                modifier = Modifier.padding(end = 7.5.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = AppColors.grey2
            )
            BasicTextField(
                onValueChange = { idTextFieldValue = it },
                value = idTextFieldValue,
                decorationBox = { innerTextField ->
                    if (idTextFieldValue.text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.member_search_text_field_hint),
                            style = AppTypography.LB1_13,
                            color = AppColors.grey2
                        )
                    } else {
                        Text(text = idTextFieldValue.text, style = AppTypography.LB1_13)
                    }

                },
            )
        }

    }

}

@Composable
fun MemberList() {
    LazyColumn {
        items(10) {
            MemberItem(name = "Member$it")
            Divider(
                color = AppColors.grey3,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}

@Composable
fun MemberItem(name: String) {
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
            painter = painterResource(id = R.drawable.sample_member_image),
            contentDescription = null
        )
        Text(text = name, style = AppTypography.B2_14, color = Color(0xFF1B1A57))
        Box(
            modifier = Modifier
                .weight(1f)
                .size(28.dp), contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_checked),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateFamilyScreen() {
    FamilyMomentsTheme {
        SearchMemberScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMemberItem() {
    MemberItem("Member")
}

@Preview(showBackground = true)
@Composable
fun PreviewMemberList() {
    MemberList()
}
