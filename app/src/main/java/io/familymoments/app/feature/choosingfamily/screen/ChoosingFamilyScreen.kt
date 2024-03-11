package io.familymoments.app.feature.choosingfamily.screen

import android.content.Intent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.familymoments.app.R
import io.familymoments.app.core.component.AppBarScreen
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.feature.bottomnav.activity.MainActivity
import io.familymoments.app.feature.creatingfamily.screen.SetAlarmScreen
import io.familymoments.app.feature.creatingfamily.screen.CopyInvitationLinkScreen
import io.familymoments.app.feature.creatingfamily.screen.SearchMemberScreen
import io.familymoments.app.feature.creatingfamily.screen.SetProfileScreen
import io.familymoments.app.feature.joiningfamily.screen.JoinScreen

enum class ChoosingFamilyRoute {
    START,
    SEARCH_MEMBER,
    SET_PROFILE,
    SET_ALARM,
    COPY_INVITATION_LINK,
    JOIN
}

@Composable
fun ChoosingFamilyScreen() {
    val context = LocalContext.current
    val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    AppBarScreen(title = {
        Text(
            text = stringResource(R.string.choosing_family_app_bar_screen_header),
            style = AppTypography.SH3_16,
            color = AppColors.deepPurple1
        )
    }) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ChoosingFamilyRoute.START.name) {
            composable(ChoosingFamilyRoute.START.name) {
                StartScreen(
                    { navController.navigate(ChoosingFamilyRoute.SEARCH_MEMBER.name) },
                    { navController.navigate(ChoosingFamilyRoute.JOIN.name) },
                    {
                        context.startActivity(mainActivityIntent)
                    })
            }
            composable(ChoosingFamilyRoute.SEARCH_MEMBER.name) {
                SearchMemberScreen(
                    navigate = { navController.navigate(ChoosingFamilyRoute.SET_PROFILE.name) },
                    viewModel = hiltViewModel()
                )
            }
            composable(ChoosingFamilyRoute.SET_PROFILE.name) {
                SetProfileScreen { navController.navigate(ChoosingFamilyRoute.SET_ALARM.name) }
            }
            composable(ChoosingFamilyRoute.SET_ALARM.name) {
                SetAlarmScreen { navController.navigate(ChoosingFamilyRoute.COPY_INVITATION_LINK.name) }
            }
            composable(ChoosingFamilyRoute.COPY_INVITATION_LINK.name) {
                CopyInvitationLinkScreen {
                    context.startActivity(mainActivityIntent)
                }
            }
            composable(ChoosingFamilyRoute.JOIN.name) {
                JoinScreen {
                    context.startActivity(mainActivityIntent)
                }
            }
        }
    }

}

@Preview
@Composable
fun ChoosingFamilyScreenPreview() {
    FamilyMomentsTheme {
        ChoosingFamilyScreen()
    }
}
