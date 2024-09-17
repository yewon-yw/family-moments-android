package io.familymoments.app.core.util

import io.familymoments.app.core.network.dto.response.Post
import io.familymoments.app.core.util.DateFormatter.dateTimeToLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// 게시글 날짜 출력 형식 지정
fun String.formattedPostDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
    val date = LocalDate.parse(this, inputFormatter)

    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.getDefault())
    return date.format(outputFormatter)
}

// 게시글 날짜를 사용자의 로컬 시간대로 변환
fun List<Post>.convertCreatedAtToLocalDate(): List<Post> {
    return this.map { post ->
        post.copy(createdAt = dateTimeToLocalDate(post.createdAt))
    }
}
