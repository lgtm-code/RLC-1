package rlc.webtoon.api.payment.infra.portone.dto

import rlc.webtoon.api.payment.infra.portone.annotation.PortOnePaymentAnnotation


data class PortOnePaymentResponse(
        private val code:Int,
        val message: String?,
        val response: PortOnePaymentAnnotation?
) {
    fun isSuccess() = code == 0
}
