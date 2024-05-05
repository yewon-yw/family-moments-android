package io.familymoments.app.feature.familysettings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.familymoments.app.R

sealed class FamilySettingNavItem(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val labelResId: Int
) {
    data object ModifyFamilyInfo : FamilySettingNavItem(
        FamilySettingRoute.MODIFY_FAMILY_INFO.name,
        R.drawable.ic_family_setting_modify_family_info,
        R.string.family_setting_label_modify_family_info
    )

    data object InviteFamilyLink : FamilySettingNavItem(
        FamilySettingRoute.INVITE_FAMILY_LINK.name,
        R.drawable.ic_family_setting_invite_family_link,
        R.string.family_setting_label_invite_family_link
    )

    data object AddFamilyMember : FamilySettingNavItem(
        FamilySettingRoute.ADD_FAMILY_MEMBER.name,
        R.drawable.ic_family_setting_add_family_member,
        R.string.family_setting_label_add_family_member
    )

    data object ChangeUploadCycle : FamilySettingNavItem(
        FamilySettingRoute.CHANGE_UPLOAD_CYCLE.name,
        R.drawable.ic_family_setting_change_upload_cycle,
        R.string.family_setting_label_change_upload_cycle
    )

    data object TransferFamilyPermission : FamilySettingNavItem(
        FamilySettingRoute.TRANSFER_FAMILY_PERMISSION.name,
        R.drawable.ic_family_setting_transfer_family_permission,
        R.string.family_setting_label_transfer_family_permission
    )

    data object RemoveFamilyMember : FamilySettingNavItem(
        FamilySettingRoute.REMOVE_FAMILY_MEMBER.name,
        R.drawable.ic_family_setting_remove_family_member,
        R.string.family_setting_label_remove_family_member
    )

    data object LeaveFamily : FamilySettingNavItem(
        FamilySettingRoute.LEAVE_FAMILY.name,
        R.drawable.ic_family_setting_leave_family,
        R.string.family_setting_label_leave_family
    )

    data object DeleteFamily : FamilySettingNavItem(
        FamilySettingRoute.DELETE_FAMILY.name,
        R.drawable.ic_family_setting_delete_family,
        R.string.family_setting_label_delete_family
    )
}

enum class FamilySettingRoute {
    MODIFY_FAMILY_INFO,
    INVITE_FAMILY_LINK,
    ADD_FAMILY_MEMBER,
    CHANGE_UPLOAD_CYCLE,
    TRANSFER_FAMILY_PERMISSION,
    REMOVE_FAMILY_MEMBER,
    LEAVE_FAMILY,
    DELETE_FAMILY
}
