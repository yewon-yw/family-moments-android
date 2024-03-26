package io.familymoments.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography


@Composable
fun PostDropdownMenu(
    items: List<Pair<String, () -> Unit>>,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
        modifier = modifier.then(
            Modifier
                .width(112.dp)
                .background(color = AppColors.deepPurple3, shape = RoundedCornerShape(5.dp))
        )
    )
    {
        items.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = it.first,
                        style = AppTypography.LB2_11,
                        color = AppColors.grey6
                    )
                },
                onClick = {
                    onExpandedChange(false)
                    it.second()
                },
                modifier = Modifier.height(35.dp)
            )
        }
    }
}
