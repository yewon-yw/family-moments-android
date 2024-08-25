package io.familymoments.app.core.component.popup

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun FamilyPermissionPopup(
    showPermissionPopup: Boolean = false,
    navigateBack: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (showPermissionPopup) {
        CompletePopUp(
            content = stringResource(id = R.string.check_family_permission_popup_content),
            dismissText = stringResource(id = R.string.check_family_permission_popup_btn),
            buttonColors = ButtonDefaults.buttonColors(containerColor = AppColors.purple2),
            onDismissRequest = {
                onDismissRequest()
                navigateBack()
            }
        )
    }
}

@Composable
fun LeaveFamilyPopup(
    showPermissionPopup: Boolean = false,
    navigateBack: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (showPermissionPopup) {
        CompletePopUp(
            content = stringResource(id = R.string.leave_family_popup_content),
            dismissText = stringResource(id = R.string.leave_family_popup_btn),
            textStyle = AppTypography.BTN5_16,
            buttonColors = ButtonDefaults.buttonColors(containerColor = AppColors.purple2),
            onDismissRequest = {
                onDismissRequest()
                navigateBack()
            }
        )
    }
}

@Composable
fun FamilySettingCompletePopup(
    showCompletePopup: Boolean = false,
    navigateBack: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (showCompletePopup) {
        CompletePopUp(
            content = stringResource(id = R.string.family_setting_complete_popup_content),
            dismissText = stringResource(id = R.string.family_setting_complete_popup_btn),
            buttonColors = ButtonDefaults.buttonColors(containerColor = AppColors.purple2),
            onDismissRequest = {
                onDismissRequest()
                navigateBack()
            }
        )
    }
}

@Composable
fun RemoveFamilyMemberPopup(
    showRemovePopup: Boolean,
    nicknames: List<String> = emptyList(),
    userIds: List<String> = emptyList(),
    removePopupDismissRequest: () -> Unit = {},
    navigateToConfirmScreen: (List<String>) -> Unit = {},
) {
    if (showRemovePopup) {
        RemoveFamilyMemberPopup(
            nicknames = nicknames,
            onDismissRequest = removePopupDismissRequest,
            onDoneButtonClicked = {
                removePopupDismissRequest()
                navigateToConfirmScreen(userIds)
            }
        )
    }
}
