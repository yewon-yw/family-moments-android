package io.familymoments.app.core.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun dateTimeToDuration(dateTime: String): Long {
        val utcDateTime =
            ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC")))
        val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault()) // 사용자의 로컬 시간대로 변환
        val now = ZonedDateTime.now(ZoneId.systemDefault()) // 현재 시간
        val durationSeconds = now.toEpochSecond() - localDateTime.toEpochSecond() // 경과 시간 계산
        return durationSeconds
    }

    fun formatDaysSince(createdAt: String): String {
        val dateTime = createdAt.replace(' ', 'T')
        val day = 60 * 60 * 24
        return "${dateTimeToDuration(dateTime) / day + 1}"
    }
}
