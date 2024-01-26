package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.FamilyMomentsTheme


@Composable
fun FamilySelectScreen(name: String, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_create_family_eclipse), contentDescription = null,
        tint = Color.Unspecified
    )
    Text(text = "우리 가족\n\n생성하기", fontSize = 36.sp, color = AppColors.deepPurple1, fontWeight = FontWeight.Bold)
    Icon(
        painter = painterResource(id = R.drawable.ic_join_family_eclipse), contentDescription = null,
        tint = Color.Unspecified
    )
}

@Preview(showBackground = true)
@Composable
fun FamilySelectScreenPreview() {
    FamilyMomentsTheme {
        FamilySelectScreen("Android")
    }
}
