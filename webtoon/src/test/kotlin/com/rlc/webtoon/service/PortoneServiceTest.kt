package com.rlc.webtoon.service

import com.rlc.webtoon.dto.request.PaymentDto
import com.rlc.webtoon.dto.response.portone.PortonePaymentResponse
import com.rlc.webtoon.dto.response.portone.PortoneAccessTokenResponse
import com.rlc.webtoon.profile_test.TestProfilePortoneService
import com.rlc.webtoon.service.inter.PortoneInterface
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.UUID

@SpringBootTest(classes = [TestProfilePortoneService::class])
@ActiveProfiles("test")
class PortoneServiceTest @Autowired constructor(
    private val portoneInterface: PortoneInterface,
) {

    @Test
    @DisplayName("아임포트 액세스 토큰이 정상 발급된다.")
    fun accessToken() {

        val portonePaymentResponse: PortoneAccessTokenResponse = portoneInterface.getAccessToken(
            impKey = "testKey",
            impSecret = "testSecret"
        )

        assertThat(portonePaymentResponse.code).isEqualTo(0)
    }

    @Test
    @DisplayName("아임포트 결제가 정상요청 된다.")
    fun payment() {
        val paymentDto = PaymentDto.fixture()

        val portonePaymentResponse: PortonePaymentResponse = portoneInterface.payment(
            accessToken = "",
            merchantUid = UUID.randomUUID().toString(),
            price = paymentDto.productType.price,
            cardNumber = paymentDto.cardNumber,
            expiry = paymentDto.expiry,
            birth = paymentDto.birth,
            cardPassword = paymentDto.cardPassword,
            productName = paymentDto.productType.productName
        )

        assertThat(portonePaymentResponse.code).isEqualTo(0)
    }

    @Test
    @DisplayName("아임포트 결제 취소요청이 정상 처리된다.")
    fun cancel() {
        val portonePaymentResponse: PortonePaymentResponse = portoneInterface.cancel(
            impUid = "testUid",
            merchantUid = UUID.randomUUID().toString(),
            price = 10
        )

        assertThat(portonePaymentResponse.code).isEqualTo(0)
    }
}