package io.familymoments.app.core.util

import io.familymoments.app.core.network.dto.response.Post
import io.familymoments.app.core.util.DateFormatter.dateTimeToLocalDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 게시글 날짜 출력 형식 지정
fun String.formattedPostDate(): String {
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
