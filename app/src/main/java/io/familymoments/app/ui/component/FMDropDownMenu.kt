package io.familymoments.app.ui.component

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FMDropDownMenu(menuItems: List<FMDropDownMenuItem>, isMenuExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(expanded = isMenuExpanded, onDismissRequest = onDismissRequest) {
        menuItems.forEach {
            DropdownMenuItem(onClick = it.onClick) {
                Text(text = it.name)
            }
        }
    }
}
