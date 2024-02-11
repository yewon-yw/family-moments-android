package io.familymoments.app.ui.choosingfamily.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SetAlarmScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.CopyInvitationLinkScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SearchMemberScreen
import io.familymoments.app.ui.choosingfamily.creating.ui.screen.SetProfileScreen
import io.familymoments.app.ui.choosingfamily.joining.ui.screen.JoinScreen

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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ChoosingFamilyRoute.START.name) {
        composable(ChoosingFamilyRoute.START.name) { StartScreen(navController) }
        composable(ChoosingFamilyRoute.SEARCH_MEMBER.name) { SearchMemberScreen(navController) }
        composable(ChoosingFamilyRoute.SET_PROFILE.name) { SetProfileScreen(navController) }
        composable(ChoosingFamilyRoute.SET_ALARM.name) { SetAlarmScreen(navController) }
        composable(ChoosingFamilyRoute.COPY_INVITATION_LINK.name) { CopyInvitationLinkScreen() }
        composable(ChoosingFamilyRoute.JOIN.name) { JoinScreen()}
    }
}
