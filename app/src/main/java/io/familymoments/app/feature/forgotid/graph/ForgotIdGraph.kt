package io.familymoments.app.feature.forgotid.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.familymoments.app.feature.forgotid.screen.FindIdScreen
import io.familymoments.app.feature.forgotid.screen.VerifyIdScreen

fun NavGraphBuilder.forgotIdGraph(navController: NavController, goToLogin:()->Unit, goToForgotPwd:()->Unit) {
    composable(route = ForgotIdRoute.Verify.route) {
        VerifyIdScreen(hiltViewModel()) { userId -> navController.navigate(ForgotIdRoute.Find.getRoute(userId)) }
    }
    composable(route = ForgotIdRoute.Find.routeWithArs, arguments = ForgotIdRoute.Find.arguments) {backStackEntry ->
        FindIdScreen(backStackEntry.arguments?.getString(ForgotIdRoute.Find.userIdArg) ?: "", goToLogin, goToForgotPwd)
    }
}

sealed interface ForgotIdRoute {
    val route: String

    data object Verify : ForgotIdRoute {
        override val route: String = "Verify"
    }

    data object Find : ForgotIdRoute {
        override val route: String = "Find"
        const val userIdArg = "userId"
        val routeWithArs = "${route}?$userIdArg={$userIdArg}"
        val arguments = listOf(navArgument(userIdArg) { type = NavType.StringType })

        fun getRoute(userId: String): String = "${route}?$userIdArg=$userId"
    }
}
