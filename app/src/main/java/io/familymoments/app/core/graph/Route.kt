package io.familymoments.app.core.graph

import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.familymoments.app.feature.deletefamily.graph.DeleteFamilyRoute
import io.familymoments.app.feature.profile.graph.ProfileScreenRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed interface Route {
    val route: String

    data object CalendarDay : Route {
        override val route: String = "CalendarDay"
        const val localDateStringArgs = "localDateString"
        val routeWithArs = "$route?$localDateStringArgs={$localDateStringArgs}"
        val arguments = listOf(navArgument(localDateStringArgs) { type = NavType.StringType })

        fun getRoute(localDateString: String): String = "$route?$localDateStringArgs=$localDateString"
    }

    data object ProfileEdit : Route {
        override val route: String = ProfileScreenRoute.Edit.name
        const val nicknameArg = "nickname"
        const val profileImgArg = "profileImg"
        val routeWithArgs = "$route/{$nicknameArg}/{$profileImgArg}"
        val arguments = listOf(
            navArgument(nicknameArg) { type = NavType.StringType },
            navArgument(profileImgArg) { type = NavType.StringType }
        )

        fun getRoute(nickname: String, profileImg: String): String {
            val encodedProfileImgUrl = URLEncoder.encode(profileImg, StandardCharsets.UTF_8.toString())
            return "$route/$nickname/$encodedProfileImgUrl"
        }
    }

    data object EditPost : Route {
        override val route: String = "EditPost"
        const val modeArg = "mode"
        const val editPostIdArg = "editPostId"
        const val editImagesArg = "editImages"
        const val editContentArg = "editContent"
        val routeWithArgs =
            "$route?$modeArg={$modeArg}&$editPostIdArg={$editPostIdArg}&$editImagesArg={$editImagesArg}&$editContentArg={$editContentArg}"
        val arguments = listOf(
            navArgument(modeArg) {
                nullable = false
                type = NavType.IntType
            },
            navArgument(editPostIdArg) {
                nullable = false
                type = NavType.LongType
            },
            navArgument(editImagesArg) {
                nullable = false
                type = NavType.StringArrayType
            },
            navArgument(editContentArg) {
                nullable = false
                type = NavType.StringType
            }
        )

        fun getRoute(mode: Int, editPostId: Long, editImages: List<String>, editContent: String): String {
            val encodedImageUrls: List<String> =
                editImages.map { URLEncoder.encode(it, StandardCharsets.UTF_8.toString()) }
            val encodedContent = URLEncoder.encode(editContent, StandardCharsets.UTF_8.toString())

            return "$route?$modeArg=$mode&$editPostIdArg=$editPostId&$editImagesArg=$encodedImageUrls&$editContentArg=$encodedContent"
        }
    }

    data object RemoveFamilyMember : Route {
        override val route: String = "RemoveFamilyMemberConfirm"
        const val userIdsArg = "userIds"
        val routeWithArgs = "$route/{$userIdsArg}"
        val arguments = listOf(navArgument(userIdsArg) { type = NavType.StringArrayType })

        fun getRoute(userIds: List<String>): String = "$route/$userIds"
    }

    data object DeleteFamily : Route {
        override val route: String = DeleteFamilyRoute.ENTER_FAMILY_NAME.name
        const val familyNameArgs = "familyName"
        val routeWithArgs = "$route/{$familyNameArgs}"
        val arguments = listOf(navArgument(familyNameArgs) { type = NavType.StringType })

        fun getRoute(familyName: String) = "$route/$familyName"
    }
}
