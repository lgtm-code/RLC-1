package rlc.webtoon.api.payment.infra.portone.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RequestCancelPayment(
        val impUid: String,
        val amount: Int?, // 취소할 금액 (null 이면 전액 취소)
        val reason: String? = null, // 취소 사유
)
