package com.rlc.webtoon.dto.response

import com.rlc.webtoon.dto.enumerated.PaymentType
import java.time.Instant

data class WebtoonPaymentResponse(
    val id: Long,
    val webtoonTitle: String,
    val webtoonEpisode: String,

    val paymentType: PaymentType,
    val fee: Int,
    val createDate: Instant,
    val updateDate: Instant,
    val viewPossibleDate: Instant
)
