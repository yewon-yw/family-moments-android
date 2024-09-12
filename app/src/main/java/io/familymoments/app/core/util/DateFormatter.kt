package io.familymoments.app.core.util

import io.familymoments.app.core.network.dto.response.Post
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateFormatter {
    private fun utcToLocalDateTime(dateTime: String?): ZonedDateTime {
        val validDateTime = dateTime ?: return ZonedDateTime.now(ZoneId.systemDefault()) // 기본값 현재 시간으로 설정
        val utcDateTime =
            ZonedDateTime.parse(validDateTime, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC")))
        return utcDateTime.withZoneSameInstant(ZoneId.systemDefault()) // 사용자의 로컬 시간대로 변환
    }

    fun dateTimeToDuration(dateTime: String): Long {
        val localDateTime = utcToLocalDateTime(dateTime)
        val now = ZonedDateTime.now(ZoneId.systemDefault()) // 현재 시간
        val durationSeconds = now.toEpochSecond() - localDateTime.toEpochSecond() // 경과 시간 계산
        return durationSeconds
    }

    // 가족 생성일로부터 지난 날짜 계산
    fun formatDaysSince(createdAt: String): String {
        val dateTime = createdAt.replace(' ', 'T')
        val day = 60 * 60 * 24
        return "${dateTimeToDuration(dateTime) / day + 1}"
    }

    fun dateTimeToLocalDate(dateTime: String?): String {
        val localDateTime = utcToLocalDateTime(dateTime)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(formatter)
    }

    // 게시글 날짜 출력 형식 지정
    fun String.formattedDate(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat("yyyy.MM.dd(EEE)", Locale.KOREA)
        return outputFormat.format(date ?: Date())
    }

    // 게시글 날짜를 사용자의 로컬 시간대로 변환
    fun List<Post>.convertCreatedAtToLocalDate(): List<Post> {
        return this.map { post ->
            post.copy(createdAt = dateTimeToLocalDate(post.createdAt))
        }
    }
}
