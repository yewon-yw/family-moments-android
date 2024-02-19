package io.familymoments.app.ui.component

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.familymoments.app.R

@Composable
fun ImageSelectDropDownMenu(isMenuExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(expanded = isMenuExpanded, onDismissRequest = onDismissRequest) {
       DropdownMenuItem(onClick = { /*TODO*/ }) {
           Text(text = stringResource(id = R.string.drop_down_menu_item_gallery_select))
       }
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.drop_down_menu_item_default_image))
        }
    }
}
