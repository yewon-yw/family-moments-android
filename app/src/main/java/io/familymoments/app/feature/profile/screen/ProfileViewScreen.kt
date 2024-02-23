package io.familymoments.app.feature.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun ProfileViewScreen(navigateToProfileEdit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(id = R.string.profile_title),
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = Modifier.size(24.dp))
            Box(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_profile_test),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_profile_modify_button),
                contentDescription = "profile_modify_button",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navigateToProfileEdit() }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "딸래미",
            style = AppTypography.H2_24,
            color = AppColors.black4
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "bimalstha291@gmail.com",
            style = AppTypography.B1_16,
            color = AppColors.grey7
        )
        Divider(
            color = AppColors.grey8, thickness = 0.6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 29.dp, bottom = 13.dp)
        )
        InfoBox()
        Divider(
            color = AppColors.grey8, thickness = 0.6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 138.dp, bottom = 100.dp)
        )
    }
}

@Composable
private fun InfoBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .weight(1f)
                .background(
                    brush = Brush.linearGradient(
                        colors = GradientColors.purpleGradient,
                        start = Offset.Infinite,
                        end = Offset.Zero
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.profile_total_upload),
                    style = AppTypography.BTN6_13,
                    color = Color.White,
                    modifier = Modifier.padding(start = 11.dp, top = 14.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 3.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 11.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Text(
                            text = "8",
                            style = AppTypography.LB1_13,
                            color = AppColors.purple2,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .weight(1f)
                .background(
                    brush = Brush.linearGradient(
                        colors = GradientColors.pinkGradient,
                        start = Offset.Infinite,
                        end = Offset.Zero
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.profile_app_usage_period),
                    style = AppTypography.BTN6_13,
                    color = Color.White,
                    modifier = Modifier.padding(start = 11.dp, top = 14.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 3.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 11.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Text(
                            text = "134",
                            style = AppTypography.LB1_13,
                            color = AppColors.pink1,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

private object GradientColors {
    val purpleGradient = listOf(AppColors.purple2, AppColors.purple2.copy(alpha = 0.56f))
    val pinkGradient = listOf(AppColors.pink1, AppColors.pink1.copy(alpha = 0.56f))
}


@Preview(showBackground = true)
@Composable
fun ProfileViewScreenPreview() {
    ProfileViewScreen(navigateToProfileEdit = {})
}
