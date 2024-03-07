package rlc.webtoon.api.payment.infra.portone.annotation

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PortOnePaymentAnnotation(
	val impUid: String,
	val merchantUid: String,
	val amount: Int,
	val cancelAmount: Int,
	val name: String?,
	val payMethod: String?,
	val channel: String?,
	val pgProvider: String?,
	val bankCode: String?,
	val bankName: String?,
	val cardCode: String?,
	val cardName: String?,
	val cardNumber: String?,
	val cardType: Int?, // 0: 신용 1: 체크
	val startedAt: Int?, // unix timestamp
	val paidAt: Int?, // unix timestamp
	val failedAt: Int?, // unix timestamp
	val cancelledAt: Int?, // unix timestamp
	val failReason: String?,
	val cancelReason: String?,
	val customerUid: String?,
	val cancelHistory: List<PortOnePaymentCancelAnnotation>?
)