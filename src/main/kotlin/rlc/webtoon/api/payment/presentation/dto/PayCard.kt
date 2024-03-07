package rlc.webtoon.api.payment.presentation.dto

import rlc.webtoon.api.common.util.CardValidator

data class PayCard(
        val cardNumber: String,
        val pw2digit: String,
        val expiry: String,
        val birth: String
) {
    init {
        CardValidator.validateUserCard(
                birth = birth,
                expiry = expiry,
                pw2digit = pw2digit,
                cardNumber = cardNumber
        )
    }
}