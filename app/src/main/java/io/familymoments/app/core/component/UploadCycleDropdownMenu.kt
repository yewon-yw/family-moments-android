package io.familymoments.app.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.util.removeDropdownPadding
import io.familymoments.app.feature.creatingfamily.UploadCycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadCycleDropdownMenu(
    onItemClicked: (UploadCycle) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(TextFieldValue()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (expanded) {
                        Modifier.border(
                            width = 1.5.dp,
                            color = AppColors.purple2,
                            shape = RoundedCornerShape(8.dp)
                        )
                    } else {
                        Modifier
                    }
                )
                .menuAnchor()
                .background(AppColors.grey6, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp, horizontal = 11.dp),
        ) {
            BasicTextField(
                value = selectedValue,
                onValueChange = { selectedValue = it },
                textStyle = AppTypography.LB1_13.copy(AppColors.black1),
                readOnly = true
            ) { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (selectedValue.text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.alarm_cycle_text_field_hint),
                            style = AppTypography.LB1_13,
                            color = AppColors.grey2
                        )
                    }
                    innerTextField()
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_drop_down_expanded_trailing),
                            contentDescription = null,
                            tint = if (expanded) Color.Unspecified else AppColors.grey2,
                            modifier = if (expanded) Modifier else Modifier.rotate(180f)
                        )
                    }
                }
            }
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .exposedDropdownSize()
                .removeDropdownPadding()
                .clip(RoundedCornerShape(8.dp))
                .background(AppColors.grey6)
        ) {
            UploadCycle.entries.forEachIndexed {index, item ->
                DropdownMenuItem(
                    text = { Text(text = item.value, style = AppTypography.LB1_13, color = AppColors.black1) },
                    onClick = {
                        expanded = !expanded
                        selectedValue = TextFieldValue(item.value)
                        onItemClicked(item)
                    }
                )
                if (index < UploadCycle.entries.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = AppColors.grey3
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadCycleDropdownPreview() {
    UploadCycleDropdownMenu()
}
