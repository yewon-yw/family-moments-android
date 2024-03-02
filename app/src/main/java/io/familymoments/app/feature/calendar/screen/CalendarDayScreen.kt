package io.familymoments.app.feature.calendar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.calendar.viewmodel.CalendarDayViewModel

@Composable
fun CalendarDayScreen(
    modifier: Modifier,
    viewModel: CalendarDayViewModel,
) {
    Column(modifier = modifier) {
        CalendarHeader(
            formattedYearMonth = "2023.08.16",
            onClickPrevMonth = {},
            onClickNextMonth = {}
        )
        LazyColumn {

        }
    }
}

@Composable
private fun CalendarHeader(
    formattedYearMonth: String,
    onClickPrevMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 40.dp, bottom = 11.dp)
            .padding(horizontal = 16.dp)
            .height(34.dp)
            .fillMaxWidth()
            .background(color = AppColors.deepPurple2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(modifier = Modifier.size(32.dp), onClick = onClickPrevMonth) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_previous_arrow),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = formattedYearMonth,
            style = AppTypography.SH2_18,
            color = AppColors.grey6,
            textAlign = TextAlign.Center
        )
        IconButton(modifier = Modifier.size(32.dp), onClick = onClickNextMonth) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_next_arrow),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDayScreenPreview() {
//    CalendarDayScreen(modifier = Modifier)
}
