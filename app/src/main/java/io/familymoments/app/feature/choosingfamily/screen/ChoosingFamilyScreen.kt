package io.familymoments.app.feature.choosingfamily.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.familymoments.app.R
import io.familymoments.app.core.component.AppBarScreen
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.core.util.GlobalTempValues
import io.familymoments.app.feature.bottomnav.activity.MainActivity
import io.familymoments.app.feature.choosingfamily.route.ChoosingFamilyRoute
import io.familymoments.app.feature.creatingfamily.screen.CopyInvitationLinkScreen
import io.familymoments.app.feature.creatingfamily.screen.SetAlarmScreen
import io.familymoments.app.feature.creatingfamily.screen.SetProfileScreen
import io.familymoments.app.feature.creatingfamily.viewmodel.CreatingFamilyViewModel
import io.familymoments.app.feature.joiningfamily.screen.JoinFamilyScreen
import io.familymoments.app.feature.joiningfamily.viewmodel.JoinFamilyViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ChoosingFamilyScreen(
    creatingFamilyViewModel: CreatingFamilyViewModel,
    joinFamilyViewModel: JoinFamilyViewModel
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val context = LocalContext.current
    val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val navigationIcon = @Composable {
        if (currentRoute != ChoosingFamilyRoute.Start.route && currentRoute != ChoosingFamilyRoute.CopyInvitationLink.routeWithArgs) {
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable { navController.popBackStack() },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = AppColors.grey3
            )
        }
    }
    AppBarScreen(
        title = {
            Text(
                text = stringResource(R.string.choosing_family_app_bar_screen_header),
                style = AppTypography.SH3_16,
                color = AppColors.grey8
            )
        },
        navigationIcon = navigationIcon
    ) {

        NavHost(navController = navController, startDestination = getStartDestination(currentRoute)) {
            composable(ChoosingFamilyRoute.Start.route) {
                StartScreen(
                    { navController.navigate(ChoosingFamilyRoute.SetProfile.route) },
                    { navController.navigate(ChoosingFamilyRoute.Join.route) },
                )
            }
            composable(ChoosingFamilyRoute.SetProfile.route) {
                SetProfileScreen(viewModel = creatingFamilyViewModel) {
                    navController.navigate(ChoosingFamilyRoute.SetAlarm.route)
                }
            }
            composable(
                route = ChoosingFamilyRoute.SetAlarm.route
            ) {

                SetAlarmScreen(viewModel = creatingFamilyViewModel) {
                    val encodedUrl = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                    navController.navigate(ChoosingFamilyRoute.CopyInvitationLink.getRoute(encodedUrl))
                }
            }
            composable(
                route = ChoosingFamilyRoute.CopyInvitationLink.routeWithArgs,
                arguments = listOf(navArgument(ChoosingFamilyRoute.CopyInvitationLink.inviteLinkStringArgs) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                CopyInvitationLinkScreen(
                    backStackEntry.arguments?.getString(ChoosingFamilyRoute.CopyInvitationLink.inviteLinkStringArgs)
                        ?: ""
                ) {
                    context.startActivity(mainActivityIntent)
                }
            }
            composable(ChoosingFamilyRoute.Join.route) {
                JoinFamilyScreen(joinFamilyViewModel) {
                    context.startActivity(mainActivityIntent)
                }
            }
        }
    }
}


private fun getStartDestination(currentRoute: String?): String {
    return if (GlobalTempValues.invitationCode.isEmpty() && currentRoute != ChoosingFamilyRoute.Join.route) ChoosingFamilyRoute.Start.route else ChoosingFamilyRoute.Join.route
}

@Preview
@Composable
fun ChoosingFamilyScreenPreview() {
    FamilyMomentsTheme {
        ChoosingFamilyScreen(hiltViewModel(), hiltViewModel())
    }
}
