package io.familymoments.app.ui.choosingfamily.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.familymoments.app.R
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SetAlarmScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.CopyInvitationLinkScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SearchMemberScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SetProfileScreen
import io.familymoments.app.ui.choosingfamily.joining.ui.screen.JoinScreen
import io.familymoments.app.ui.component.AppBarScreen
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography

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
    AppBarScreen(title = {
        Text(
            text = stringResource(R.string.choosing_family_app_bar_screen_header),
            style = AppTypography.SH3_16,
            color = AppColors.deepPurple1
        )
    }) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ChoosingFamilyRoute.START.name) {
            composable(ChoosingFamilyRoute.START.name) { StartScreen(navController) }
            composable(ChoosingFamilyRoute.SEARCH_MEMBER.name) { SearchMemberScreen(navController) }
            composable(ChoosingFamilyRoute.SET_PROFILE.name) { SetProfileScreen(navController) }
            composable(ChoosingFamilyRoute.SET_ALARM.name) { SetAlarmScreen(navController) }
            composable(ChoosingFamilyRoute.COPY_INVITATION_LINK.name) { CopyInvitationLinkScreen() }
            composable(ChoosingFamilyRoute.JOIN.name) { JoinScreen() }
        }
    }

}
