package io.familymoments.app.core.graph

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.core.util.scaffoldState
import io.familymoments.app.feature.bottomnav.graph.bottomNavGraph
import io.familymoments.app.feature.postdetail.screen.PostDetailScreen
import io.familymoments.app.feature.profile.graph.profileGraph

fun getMainGraph(
    navController: NavController
): NavGraphBuilder.() -> Unit = {

    bottomNavGraph(navController)
    profileGraph(navController)

    composable(route = "PostDetail") {
        PostDetailScreen(
            modifier = Modifier
                .scaffoldState(
                    hasShadow = true,
                    hasBackButton = true,
                )
        )
    }
}
