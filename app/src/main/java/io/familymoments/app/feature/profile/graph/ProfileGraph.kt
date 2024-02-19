package io.familymoments.app.feature.profile.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.feature.profile.screen.ProfileEditScreen
import io.familymoments.app.feature.profile.screen.ProfileViewScreen

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(route = ProfileScreenRoute.View.name) { ProfileViewScreen(navController) }
    composable(route = ProfileScreenRoute.Edit.name) { ProfileEditScreen(navController) }
}

enum class ProfileScreenRoute {
    View,
    Edit
}
