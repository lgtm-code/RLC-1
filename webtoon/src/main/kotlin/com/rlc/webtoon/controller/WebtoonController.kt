package com.rlc.webtoon.controller

import com.rlc.webtoon.dto.common.ResponseResult
import com.rlc.webtoon.dto.request.WebtoonDto
import com.rlc.webtoon.dto.response.WebtoonResponseDto
import com.rlc.webtoon.service.WebtoonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek

@RestController
@RequestMapping("/rlc/webtoon")
class WebtoonController(
    private val webtoonService: WebtoonService
) {

    @PostMapping
    fun saveWebtoon(webtoonDto: WebtoonDto):ResponseResult<WebtoonResponseDto> {
        return ResponseResult(webtoonService.saveWebtoon(webtoonDto))
    }

    @GetMapping
    fun dayOfWeekWebtoonList(dayOfWeek: DayOfWeek): ResponseResult<List<WebtoonResponseDto>> {

        return ResponseResult(webtoonService.dayOfWeekWebtoonList(dayOfWeek))
    }
}