package io.familymoments.app.feature.mypage.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.feature.profile.graph.profileGraph

fun NavGraphBuilder.myPageGraph(navController: NavController){
    composable(route = MyPageRoute.Profile.name) {
        profileGraph(navController)
    }
    composable(route = MyPageRoute.Password.name) {
        // Password Screen
    }
    composable(route = MyPageRoute.Notification.name) {
        // Notification Screen
    }
    composable(route = MyPageRoute.FamilyInvitationList.name) {
        // FamilyInvitationList Screen
    }
    composable(route = MyPageRoute.Notification.name) {
        // Notification Screen
    }
    composable(route = MyPageRoute.FamilySettings.name) {
        // FamilySettings Screen
    }
    composable(route = MyPageRoute.Logout.name) {
        // Logout Screen
    }
    composable(route = MyPageRoute.AccountDeletion.name) {
        // AccountDeletion Screen
    }
}

enum class MyPageRoute{
    Profile,
    Password,
    Notification,
    FamilyInvitationList,
    FamilySettings,
    Logout,
    AccountDeletion,
}
