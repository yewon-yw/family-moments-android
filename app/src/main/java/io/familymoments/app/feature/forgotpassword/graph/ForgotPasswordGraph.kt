package io.familymoments.app.feature.forgotpassword.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.familymoments.app.feature.forgotpassword.screen.IdentifyScreen
import io.familymoments.app.feature.forgotpassword.screen.ResetScreen
import io.familymoments.app.feature.forgotpassword.screen.VerifyScreen

fun NavGraphBuilder.forgotPasswordGraph(navController: NavController) {
    composable(route = ForgotPasswordRoute.Identify.route) {
        IdentifyScreen(hiltViewModel()) { id -> navController.navigate(
            ForgotPasswordRoute.Verify.getRoute(id)
        ) }
    }
    composable(route = ForgotPasswordRoute.Verify.routeWithArgs, arguments =ForgotPasswordRoute.Verify.arguments ) {
        VerifyScreen(hiltViewModel()) { id -> navController.navigate(
            ForgotPasswordRoute.Reset.getRoute(id)
        ) }
    }
    composable(route =ForgotPasswordRoute.Reset.routeWithArgs, arguments = ForgotPasswordRoute.Reset.arguments) {
        ResetScreen(hiltViewModel())
    }
}

sealed interface ForgotPasswordRoute {
    val route: String

    data object Identify:ForgotPasswordRoute{
        override val route: String = "Identify"
    }

    data object Verify : ForgotPasswordRoute {
        override val route: String = "Verify"
        const val idArg = "id"
        val routeWithArgs = "$route?$idArg={$idArg}"
        val arguments = listOf(
            navArgument(idArg) {
                nullable = false
                type = NavType.StringType
            }
        )

        fun getRoute(id: String): String {
            return "${route}?${idArg}=$id"
        }
    }


    data object Reset : ForgotPasswordRoute {
        override val route: String = "Reset"
        const val idArg = "id"
        val routeWithArgs = "$route?$idArg={$idArg}"
        val arguments = listOf(
            navArgument(idArg) {
                nullable = false
                type = NavType.StringType
            }
        )

        fun getRoute(id: String): String {
            return "${route}?${idArg}=$id"
        }
    }
}
