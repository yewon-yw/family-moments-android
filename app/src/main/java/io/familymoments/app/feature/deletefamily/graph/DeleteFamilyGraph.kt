package io.familymoments.app.feature.deletefamily.graph

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
            navigateNext = {
                navController.navigate(DeleteFamilyRoute.ENTER_FAMILY_NAME.name)
            }
        )
    }
    composable(DeleteFamilyRoute.ENTER_FAMILY_NAME.name) {
        EnterFamilyNameScreen(
            modifier = Modifier.scaffoldState(hasShadow = false, hasBackButton = true),
            navigateBack = {
                navController.popBackStack()
            },
            navigateNext = {
                navController.navigate(DeleteFamilyRoute.COMPLETE.name)
            }
        )
    }
    composable(DeleteFamilyRoute.COMPLETE.name) {
        DeleteFamilyCompleteScreen(
            modifier = Modifier.scaffoldState(hasShadow = false, hasBackButton = true)
        )
    }
}

enum class DeleteFamilyRoute {
    DELETE_FAMILY,
    ENTER_FAMILY_NAME,
    COMPLETE
}

