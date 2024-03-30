package com.rlc.webtoon.profile_test

import com.rlc.webtoon.dto.response.portone.PortoneAccessTokenResponse
import com.rlc.webtoon.dto.response.portone.PortonePaymentResponse
import com.rlc.webtoon.service.inter.PortoneInterface
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class TestProfilePortoneService: PortoneInterface {

    override fun getAccessToken(impKey: String, impSecret: String): PortoneAccessTokenResponse {
        return PortoneAccessTokenResponse.successResponseFixture()
    }

    override fun payment(
        accessToken: String,
        merchantUid: String,
        price: Int,
        cardNumber: String,
        expiry: String,
        birth: String,
        cardPassword: String,
        productName: String
    ): PortonePaymentResponse {
        return PortonePaymentResponse.successResponseFixture()
    }

    override fun cancel(impUid: String, merchantUid: String, price: Int): PortonePaymentResponse {
        return PortonePaymentResponse.successResponseFixture()
    }
}