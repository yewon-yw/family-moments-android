package io.familymoments.app.feature.forgotpassword.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.feature.forgotpassword.screen.IdentifyScreen
import io.familymoments.app.feature.forgotpassword.screen.ResetScreen
import io.familymoments.app.feature.forgotpassword.screen.VerifyScreen

fun NavGraphBuilder.forgotPasswordGraph(navController: NavController) {
    composable(ForgotPasswordRoute.IDENTIFY.name) {
        IdentifyScreen(hiltViewModel()) { navController.navigate(ForgotPasswordRoute.VERIFY.name) }
    }
    composable(ForgotPasswordRoute.VERIFY.name) {
        VerifyScreen(hiltViewModel()) { navController.navigate(ForgotPasswordRoute.RESET.name) }
    }
    composable(ForgotPasswordRoute.RESET.name) {
        ResetScreen {}
    }
}

enum class ForgotPasswordRoute {
    IDENTIFY,
    VERIFY,
    RESET
}
