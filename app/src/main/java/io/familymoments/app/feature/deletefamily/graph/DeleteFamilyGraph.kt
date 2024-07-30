package io.familymoments.app.feature.deletefamily.graph

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.util.scaffoldState
import io.familymoments.app.feature.deletefamily.screen.DeleteFamilyCompleteScreen
import io.familymoments.app.feature.deletefamily.screen.DeleteFamilyScreen
import io.familymoments.app.feature.deletefamily.screen.EnterFamilyNameScreen

fun NavGraphBuilder.deleteFamilyGraph(navController: NavController) {
    composable(DeleteFamilyRoute.DELETE_FAMILY.name) {
        DeleteFamilyScreen(
            modifier = Modifier.scaffoldState(hasShadow = false, hasBackButton = true),
            navigateBack = {
                navController.popBackStack()
            },
            navigateNext = { familyName ->
                navController.navigate(Route.DeleteFamily.getRoute(familyName))
            },
            viewModel = hiltViewModel()
        )
    }
    composable(
        route = Route.DeleteFamily.routeWithArgs,
        arguments = Route.DeleteFamily.arguments
    ) { backStackEntry ->
        EnterFamilyNameScreen(
            modifier = Modifier.scaffoldState(hasShadow = false, hasBackButton = true),
            navigateBack = {
                navController.popBackStack()
            },
            navigateNext = {
                navController.navigate(DeleteFamilyRoute.COMPLETE.name)
            },
            viewModel = hiltViewModel(),
            familyName = backStackEntry.arguments?.getString(Route.DeleteFamily.familyNameArgs) ?: ""
        )
    }
    composable(DeleteFamilyRoute.COMPLETE.name) {
        DeleteFamilyCompleteScreen(
            modifier = Modifier.scaffoldState(hasShadow = false, hasBackButton = false, hasIcon = false)
        )
    }
}

enum class DeleteFamilyRoute {
    DELETE_FAMILY,
    ENTER_FAMILY_NAME,
    COMPLETE
}

