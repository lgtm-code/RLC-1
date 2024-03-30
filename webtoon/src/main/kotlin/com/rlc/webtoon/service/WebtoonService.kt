package com.rlc.webtoon.service

import com.rlc.webtoon.dto.request.WebtoonDto
import com.rlc.webtoon.dto.response.WebtoonResponseDto
import com.rlc.webtoon.entity.Webtoon
import com.rlc.webtoon.repostiory.WebtoonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
@Transactional(readOnly = true)
class WebtoonService(
    private val webtoonRepository: WebtoonRepository,
) {

    @Transactional
    fun saveWebtoon(webtoonDto: WebtoonDto): WebtoonResponseDto {
        val webtoon = webtoonDto.toEntity()
        webtoonRepository.save(webtoon)

        return WebtoonResponseDto.of(webtoon.title, webtoon.author, webtoon.dayOfWeek, webtoon.id)
    }

    /**
     * 요일별 웹툰 목록.
     * 페이징 처리 X, ref)네이버 웹툰
     */
    fun dayOfWeekWebtoonList(dayOfWeek: DayOfWeek): List<WebtoonResponseDto> {

        return webtoonRepository.findByDayOfWeek(dayOfWeek).toWebtoonList()
    }

    fun List<Webtoon>.toWebtoonList(): List<WebtoonResponseDto> {
        return this.map {webtoon ->
            WebtoonResponseDto.of(webtoon.title, webtoon.author, webtoon.dayOfWeek, webtoon.id)
        }
    }
}