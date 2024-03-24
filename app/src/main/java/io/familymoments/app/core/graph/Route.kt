package io.familymoments.app.core.graph

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface Route {
    val route: String

    data object CalendarDay : Route {
        override val route: String = "CalendarDay"
        const val localDateStringArgs = "localDateString"
        val routeWithArs = "$route?$localDateStringArgs={$localDateStringArgs}"
        val arguments = listOf(navArgument(localDateStringArgs) { type = NavType.StringType })

        fun getRoute(localDateString: String): String = "$route?$localDateStringArgs=$localDateString"
    }
}
