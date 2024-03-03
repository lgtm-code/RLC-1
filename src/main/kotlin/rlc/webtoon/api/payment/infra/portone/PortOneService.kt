package rlc.webtoon.api.payment.infra.portone

import rlc.webtoon.api.payment.infra.portone.dto.PortOnePaymentResponse
import rlc.webtoon.api.payment.presentation.dto.PayCard

interface PortOneService {
    fun pay(paymentPurpose:String, card: PayCard, amount: Int): PortOnePaymentResponse
    fun cancel(impUid: String, amount: Int, reason: String): PortOnePaymentResponse
}