package com.rlc.webtoon.service.inter

import com.rlc.webtoon.dto.response.portone.PortoneAccessTokenResponse
import com.rlc.webtoon.dto.response.portone.PortonePaymentResponse

interface PortoneInterface {

    /**
     * Import 액세스 토큰 발급.
     */
    fun getAccessToken(impKey: String, impSecret: String): PortoneAccessTokenResponse

    /**
     * Import 결제 요청.
     */
    fun payment(
        accessToken: String,
        merchantUid: String,
        price: Int,
        cardNumber: String,
        expiry: String,
        birth: String,
        cardPassword: String,
        productName: String,
    ): PortonePaymentResponse

    fun cancel(impUid: String, merchantUid: String, price: Int): PortonePaymentResponse
}