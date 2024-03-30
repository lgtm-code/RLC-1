package com.rlc.webtoon.service

import com.rlc.webtoon.dto.request.WebtoonDto
import com.rlc.webtoon.repostiory.WebtoonRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek.*

@SpringBootTest
@Transactional
class WebtoonServiceTest @Autowired constructor(
    val webtoonService: WebtoonService,
    val webtoonRepository: WebtoonRepository
) {

    @Test
    @DisplayName("웹툰 등록이 정상동작한다.")
    fun saveWebtoon() {
        webtoonService.saveWebtoon(WebtoonDto.fixture())
        
        val webtoonList = webtoonRepository.findAll()
        assertThat(webtoonList).hasSize(1)
        assertThat(webtoonList[0].title).isEqualTo("웹툰 테스트")
    }
    
    @Test
    @DisplayName("웹툰 목록조회가 정상동작한다.")
    fun webtoonList(){
        webtoonRepository.save(WebtoonDto.fixture(title = "마음의 소리", dayOfWeek = TUESDAY).toEntity())
        webtoonRepository.save(WebtoonDto.fixture(title = "김부장", dayOfWeek = TUESDAY).toEntity())
        webtoonRepository.save(WebtoonDto.fixture(title = "캐슬", dayOfWeek = WEDNESDAY).toEntity())

        val webtoonList = webtoonService.dayOfWeekWebtoonList(TUESDAY)
        assertThat(webtoonList).hasSize(2)
        assertThat(webtoonList).extracting("title").containsExactlyInAnyOrder("김부장", "마음의 소리")
    }

}