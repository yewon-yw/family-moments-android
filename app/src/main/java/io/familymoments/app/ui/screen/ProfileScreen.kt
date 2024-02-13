package io.familymoments.app.ui.screen

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.familymoments.app.R
import io.familymoments.app.ui.component.FMDropdownMenu
import io.familymoments.app.ui.component.FMTextField
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

enum class ProfileScreenRoute {
    View,
    Edit
}

object GradientColors {
    val purpleGradient = listOf(AppColors.purple2, AppColors.purple2.copy(alpha = 0.56f))
    val pinkGradient = listOf(AppColors.pink1, AppColors.pink1.copy(alpha = 0.56f))
}

@Composable
fun ProfileScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProfileScreenRoute.View.name) {
        composable(route = ProfileScreenRoute.View.name) { ViewScreen(navController) }
        composable(route = ProfileScreenRoute.Edit.name) { EditScreen(navController) }
    }
}

@Composable
fun ViewScreen(navController: NavController) {
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
                    .clickable { navController.navigate(ProfileScreenRoute.Edit.name) }
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
fun EditScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(id = R.string.edit_profile_title),
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
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
        }
        Spacer(modifier = Modifier.height(22.dp))
        FMDropdownMenu(
            onGallerySelected = { Log.d("DropdownMenu", "갤러리 선택") },
            onDefaultImageSelected = { Log.d("DropdownMenu", "기본이미지") }
        )
        Spacer(modifier = Modifier.height(34.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            CustomTextField(stringResource(id = R.string.profile_text_field_name), "홍길동", "이름 입력")
            Spacer(modifier = Modifier.height(42.dp))
            CustomTextField(stringResource(id = R.string.profile_text_field_nickname), "딸내미", "닉네임 입력")
            Spacer(modifier = Modifier.height(42.dp))
            CustomTextField(stringResource(id = R.string.profile_text_field_birth_date), "19990909", "생년월일 입력")
            Spacer(modifier = Modifier.height(31.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 11.dp, end = 11.dp),
            ) {
                Button(
                    onClick = { navController.navigate(ProfileScreenRoute.View.name) },
                    modifier = Modifier
                        .weight(1f)
                        .size(54.dp),
                    colors = ButtonDefaults.buttonColors(AppColors.purple1, Color.White),
                    shape = RoundedCornerShape(60.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_btn_cancel),
                        style = AppTypography.BTN4_18,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 34.dp))
                Button(
                    onClick = { navController.navigate(ProfileScreenRoute.View.name) },
                    modifier = Modifier
                        .weight(1f)
                        .size(54.dp),
                    colors = ButtonDefaults.buttonColors(AppColors.purple2, Color.White),
                    shape = RoundedCornerShape(60.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_btn_done),
                        style = AppTypography.BTN4_18,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(title: String, value: String, hint: String) {
    Text(
        text = title,
        style = AppTypography.B1_16,
        color = AppColors.deepPurple1,
        modifier = Modifier.padding(start = 1.dp, bottom = 2.dp)
    )
    FMTextField(text = value, placeholder = hint)
}

@Composable
fun InfoBox() {
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


@Preview(showBackground = true)
@Composable
fun ViewScreenPreview() {
    ViewScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    EditScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
