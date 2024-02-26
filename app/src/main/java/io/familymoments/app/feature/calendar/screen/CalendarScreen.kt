package io.familymoments.app.feature.calendar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.calendar.viewmodel.CalendarViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    modifier: Modifier,
    viewModel: CalendarViewModel
) {
    val calendarUiState = viewModel.calendarUiState.collectAsStateWithLifecycle()
    val daysOfweek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .align(Alignment.CenterHorizontally),
            text = "캘린더",
            style = AppTypography.B1_16,
            color = AppColors.black1
        )
        CalendarHeader(
            formattedYearMonth = calendarUiState.value.yearMonth.format(DateTimeFormatter.ofPattern("yyyy.MM")),
            onClickPrevMonth = viewModel::getPreviousMonth,
            onClickNextMonth = viewModel::getNextMonth
        )
        CalendarContent(daysOfweek, calendarUiState.value.dates)
    }
}

@Composable
private fun CalendarContent(
    daysOfweek: List<String>,
    dates: List<LocalDate>
) {
    val firstDay = dates[0]
    val dateStrings = dates.map { it.dayOfMonth.toString() }.toMutableList()

    when (firstDay.dayOfWeek) {
        DayOfWeek.MONDAY -> {
            dateStrings.add(0, "")
        }

        DayOfWeek.TUESDAY -> {
            repeat(2) {
                dateStrings.add(0, "")
            }
        }

        DayOfWeek.WEDNESDAY -> {
            repeat(3) {
                dateStrings.add(0, "")
            }
        }

        DayOfWeek.THURSDAY -> {
            repeat(4) {
                dateStrings.add(0, "")
            }
        }

        DayOfWeek.FRIDAY -> {
            repeat(5) {
                dateStrings.add(0, "")
            }
        }

        DayOfWeek.SATURDAY -> {
            repeat(6) {
                dateStrings.add(0, "")
            }
        }

        else -> {
            // do nothing
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape((16.5).dp))
            .background(color = AppColors.grey3)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .heightIn(min = (32.8).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfweek.forEach { day ->
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = day,
                    style = AppTypography.SH3_16,
                    color = AppColors.black1,
                    textAlign = TextAlign.Center
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(dateStrings.size) { index ->
                val text = transformDate(dateStrings[index])
                Column(
                    modifier = Modifier
                        .heightIn(min = 43.dp)
                        .padding(all = (3.2).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = text,
                        style = AppTypography.BTN5_16,
                        color = AppColors.black1,
                    )
                    if (text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.padding(top = 6.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_dot),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

private fun transformDate(day: String): String {
    return if (day.isEmpty()) {
        ""
    } else if (day.toInt() in 1..9) {
        "0$day"
    } else {
        day
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
            .padding(vertical = 24.dp)
            .height(64.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = AppColors.deepPurple2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onClickPrevMonth) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_previous_arrow),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = formattedYearMonth,
            style = AppTypography.H2_24,
            color = AppColors.grey6,
            textAlign = TextAlign.Center
        )
        IconButton(onClick = onClickNextMonth) {
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
fun CalendarScreenPreview() {
    CalendarScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = CalendarViewModel()
    )
}
