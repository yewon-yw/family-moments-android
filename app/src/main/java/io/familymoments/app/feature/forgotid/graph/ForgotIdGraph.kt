package io.familymoments.app.feature.forgotid.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.feature.forgotid.screen.FindScreen
import io.familymoments.app.feature.forgotid.screen.VerifyScreen

fun NavGraphBuilder.forgotIdGraph(navController: NavController) {
    composable(route = ForgotIdRoute.Verify.route) {
        VerifyScreen{navController.navigate(ForgotIdRoute.Find.route)}
    }
    composable(route = ForgotIdRoute.Find.route) {
        FindScreen()
    }
}

sealed interface ForgotIdRoute {
    val route: String

    data object Verify : ForgotIdRoute {
        override val route: String = "Verify"
    }

    data object Find : ForgotIdRoute {
        override val route: String = "Find"
    }
}
