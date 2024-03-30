package com.rlc.webtoon.dto.request

import com.rlc.webtoon.entity.WebtoonDetail
import java.time.Instant

data class WebtoonDetailRequestDto constructor(
    val episode: String, //에피소드명 ex)1화, 2화

    val content: String, //웹툰 파일이라면 어떤 타입이 맞을까?
    val displayDate: Instant
) {

    fun toEntity(): WebtoonDetail {
        return WebtoonDetail(episode, content, displayDate)
    }

    companion object {
        fun fixture(
            episode: String = "1화",
            content: String = "재밌는 내용",
            displayDate: Instant = Instant.now())
        : WebtoonDetailRequestDto {
            return WebtoonDetailRequestDto(
                episode, content, displayDate
            )
        }
    }
}