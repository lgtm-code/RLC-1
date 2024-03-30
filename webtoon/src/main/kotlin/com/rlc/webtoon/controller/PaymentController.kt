package com.rlc.webtoon.controller

import com.rlc.webtoon.dto.common.ResponseResult
import com.rlc.webtoon.dto.request.PaymentDto
import com.rlc.webtoon.dto.response.PaymentHistoryResponse
import com.rlc.webtoon.service.PaymentService
import com.rlc.webtoon.util.userUuid
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rlc/payment")
class PaymentController(
    private val paymentService: PaymentService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 아임포트 결제요청 요청.
     */
    @PostMapping
    fun payment(
        httpServletRequest: HttpServletRequest,
        @RequestBody paymentDto: PaymentDto): ResponseResult<PaymentHistoryResponse> {

        return ResponseResult(paymentService.payment(httpServletRequest.userUuid(), paymentDto))
    }

}