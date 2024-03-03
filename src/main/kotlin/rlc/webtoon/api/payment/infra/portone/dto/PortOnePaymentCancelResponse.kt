package rlc.webtoon.api.payment.infra.portone.dto

import rlc.webtoon.api.payment.infra.portone.annotation.PortOnePaymentCancelAnnotation

data class PortOnePaymentCancelResponse(
        private val code:Int,
        val message: String?,
        val response: PortOnePaymentCancelAnnotation?
) {
    fun isSuccess() = code == 0
}
