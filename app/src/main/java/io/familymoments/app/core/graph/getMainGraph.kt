package io.familymoments.app.core.graph

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.familymoments.app.core.util.scaffoldState
import io.familymoments.app.feature.bottomnav.graph.bottomNavGraph
import io.familymoments.app.feature.mypage.graph.myPageGraph
import io.familymoments.app.feature.calendar.screen.CalendarDayScreen
import io.familymoments.app.feature.postdetail.screen.PostDetailScreen
import io.familymoments.app.feature.profile.graph.profileGraph

fun getMainGraph(
    navController: NavController
): NavGraphBuilder.() -> Unit = {

    bottomNavGraph(navController)
    profileGraph(navController)
    myPageGraph(navController)

    composable(route = CommonRoute.POST_DETAIL.name) {
        PostDetailScreen(
            viewModel = hiltViewModel(),
            // todo 추후 인덱스 변경
            index = 1,
            modifier = Modifier
                .scaffoldState(
                    hasShadow = true,
                    hasBackButton = true,
                )
        )
    }

    composable(
        route = Route.CalendarDay.routeWithArs,
        arguments = Route.CalendarDay.arguments
    ) {
        CalendarDayScreen(
            modifier = Modifier.scaffoldState(
                hasShadow = true,
                hasBackButton = true,
            ),
            viewModel = hiltViewModel(),
            navigateToPostDetail = {
                navController.navigate(CommonRoute.POST_DETAIL.name)
            }
        )
    }
}

enum class CommonRoute {
    POST_DETAIL
}
