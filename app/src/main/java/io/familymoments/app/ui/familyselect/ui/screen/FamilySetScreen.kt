package io.familymoments.app.ui.familyselect.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class FamilySetRoute(val route: String) {
    FAMILY_SELECT("가족 선택"),
    FAMILY_MEMBER_SEARCH("가족 멤버 검색"),
    FAMILY_PROFILE_SET_UP("가족 프로필 설정"),
    FAMILY_ALARM_SET_UP("가족 알람 설정"),
    FAMILY_INVITATION_LINK("가족 초대 링크"),
    FAMILY_JOIN("가족 참여 화면")
}

@Composable
fun FamilySetScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = FamilySetRoute.FAMILY_SELECT.route) {
        composable(FamilySetRoute.FAMILY_SELECT.route) { FamilySelectScreen(navController) }
        composable(FamilySetRoute.FAMILY_MEMBER_SEARCH.route) { FamilyMemberSearchScreen(navController) }
        composable(FamilySetRoute.FAMILY_PROFILE_SET_UP.route) { FamilyProfileSetUpScreen(navController) }
        composable(FamilySetRoute.FAMILY_ALARM_SET_UP.route) { FamilyAlarmSetUpScreen(navController) }
        composable(FamilySetRoute.FAMILY_INVITATION_LINK.route) { FamilyInvitationLinkScreen(navController) }
        composable(FamilySetRoute.FAMILY_JOIN.route) { FamilyJoinScreen(navController)}
        // 메인 화면 추가
    }
}
