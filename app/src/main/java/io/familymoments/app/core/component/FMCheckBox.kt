package io.familymoments.app.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


enum class CheckedStatus(val number: Int) {
    UNCHECKED(0),
    CHECKED(1);

    fun toggle(): CheckedStatus {
        return when (this) {
            UNCHECKED -> CHECKED
            CHECKED -> UNCHECKED
        }
    }
}

@Composable
fun FMCheckBox(imageResources: List<Int>, defaultStatus: CheckedStatus = CheckedStatus.UNCHECKED, onCheckedChange: (CheckedStatus) -> Unit = {}) {
    var status by remember(key1 = defaultStatus) { mutableStateOf(defaultStatus) }
    Image(
            painter = painterResource(id = imageResources[status.number]),
            contentDescription = null,
            modifier = Modifier
                    .clickable {
                        status = status.toggle()
                        onCheckedChange(status)
                    }
    )
}
