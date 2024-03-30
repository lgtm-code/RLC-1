package com.rlc.webtoon.dto.request

import com.rlc.webtoon.entity.Webtoon
import jakarta.persistence.*
import java.time.DayOfWeek

data class WebtoonDto(
    val title: String,
    val author: String,
    @Enumerated(EnumType.STRING)
    val dayOfWeek: DayOfWeek, //어떤 요일 웹툰인지 설정
){
    fun toEntity(): Webtoon {
        return Webtoon(
            title = this.title,
            author = this.author,
            dayOfWeek = this.dayOfWeek
        )
    }

    companion object {
        fun fixture(
            title: String = "웹툰 테스트",
            author: String = "kyg",
            dayOfWeek: DayOfWeek = DayOfWeek.MONDAY
        ): WebtoonDto{
            return WebtoonDto(title, author, dayOfWeek)
        }
    }
}