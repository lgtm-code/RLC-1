package com.rlc.webtoon.controller

import com.rlc.webtoon.dto.common.ResponseResult
import com.rlc.webtoon.dto.request.WebtoonDetailRequestDto
import com.rlc.webtoon.dto.response.WebtoonEpisodeResponse
import com.rlc.webtoon.dto.response.WebtoonPaymentResponse
import com.rlc.webtoon.service.WebtoonDetailService
import com.rlc.webtoon.util.userUuid
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rlc/webtoon/detail")
class WebtoonDetailController(
    private val webtoonDetailService: WebtoonDetailService
) {

    /**
     * 웹툰 에피소드(상세 내용) 저장.
     */
    @PostMapping
    fun saveWebtoonDetail(
        webtoonId: Long,
        @RequestBody webtoonDetailRequest: WebtoonDetailRequestDto
    ): ResponseResult<Unit> {
        return ResponseResult(webtoonDetailService.saveWebtoonDetail(webtoonId, webtoonDetailRequest))
    }

    /**
     * 웹툰 상세 조회.
     */
    @GetMapping("/{webtoonUuid}")
    fun viewWebtoonDetail(
        @PathVariable("webtoonUuid") webtoonUuid: String,
        httpServletRequest: HttpServletRequest
    ): ResponseResult<WebtoonEpisodeResponse> {
        return ResponseResult(webtoonDetailService.viewWebtoonDetail(webtoonUuid, httpServletRequest.userUuid()))
    }

    /**
     * 웹툰 결제.
     */
    @PostMapping("/{webtoonUuid}/pay")
    fun payWebtoonDetail(@PathVariable("webtoonUuid") webtoonUuid: String,
                         httpServletRequest: HttpServletRequest
    ): ResponseResult<WebtoonPaymentResponse> {

        return ResponseResult(webtoonDetailService.payWebtoonDetail(webtoonUuid, httpServletRequest.userUuid()))
    }

    /**
     * 결제한 웹툰 환불 요청.
     * 조회하지 않은 웹툰에 대하여(결제일로부터 2일이 초과하지 않은) 환불할 수 있다.
     */
    @PatchMapping("{webtoonPaymentId}/refund")
    fun refundWebtoonDetail(
        @PathVariable("webtoonPaymentId") webtoonPaymentHistoryId: Long,
        httpServletRequest: HttpServletRequest
    ): ResponseResult<WebtoonPaymentResponse> {
        return ResponseResult(webtoonDetailService.refundPaidWebtoon(httpServletRequest.userUuid(), webtoonPaymentHistoryId))
    }

}