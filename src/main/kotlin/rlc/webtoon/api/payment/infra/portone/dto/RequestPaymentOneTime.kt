package rlc.webtoon.api.payment.infra.portone.dto

import com.fasterxml.jackson.annotation.JsonProperty


data class RequestPaymentOneTime(
        val name: String,
        @JsonProperty("merchant_uid")
        val merchantUid: String,
        @JsonProperty("card_number")
        val cardNumber: String,
        @JsonProperty("pwd_2digit")
        val pwd2digit: String,
        val expiry: String,
        val birth: String,
        val amount: Int
)
