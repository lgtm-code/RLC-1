package rlc.webtoon.api.payment.infra.portone.annotation

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PortOnePaymentCancelAnnotation(
        val pgTid: String, // PG사 승인취소번호
        val amount: Int, // 취소 금액
        val cancelledAt: Long, // 결제취소된 시각 UNIX timestamp
        val reason: String, // 결제취소 사유
)