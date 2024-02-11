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
    CHOOSING_FAMILY,
    SEARCHING_MEMBER,
    FAMILY_PROFILE_SET_UP,
    FAMILY_ALARM_SET_UP,
    FAMILY_INVITATION_LINK,
    FAMILY_JOIN
}

@Composable
fun ChoosingFamily() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ChoosingFamilyRoute.CHOOSING_FAMILY.name) {
        composable(ChoosingFamilyRoute.CHOOSING_FAMILY.name) { StartScreen(navController) }
        composable(ChoosingFamilyRoute.SEARCHING_MEMBER.name) { SearchMemberScreen(navController) }
        composable(ChoosingFamilyRoute.FAMILY_PROFILE_SET_UP.name) { SetProfileScreen(navController) }
        composable(ChoosingFamilyRoute.FAMILY_ALARM_SET_UP.name) { SetAlarmScreen(navController) }
        composable(ChoosingFamilyRoute.FAMILY_INVITATION_LINK.name) { CopyInvitationLinkScreen(navController) }
        composable(ChoosingFamilyRoute.FAMILY_JOIN.name) { JoinScreen(navController)}
        // 메인 화면 추가
    }
}
