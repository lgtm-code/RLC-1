package com.rlc.webtoon.dto.response

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.DayOfWeek

data class WebtoonResponseDto(
    val title: String,
    val author: String,
    @Enumerated(EnumType.STRING)
    val dayOfWeek: DayOfWeek,
    val id: Long?
){

    companion object {
        fun of(title: String, author: String, dayOfWeek: DayOfWeek, id: Long?): WebtoonResponseDto {
            return WebtoonResponseDto(title, author, dayOfWeek, id)
        }
    }
}
