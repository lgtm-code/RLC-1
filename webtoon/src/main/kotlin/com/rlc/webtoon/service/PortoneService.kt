package com.rlc.webtoon.service

import com.rlc.webtoon.dto.response.portone.PortoneAccessTokenResponse
import com.rlc.webtoon.dto.response.portone.PortonePaymentResponse
import com.rlc.webtoon.feign.PortoneFeignClient
import com.rlc.webtoon.service.inter.PortoneInterface
import com.rlc.webtoon.util.importCacheName
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class PortoneService(
    private val portoneFeignClient: PortoneFeignClient
): PortoneInterface {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 로컬 캐시(Caffeine 적용) ttl: 30min
     */
    @Cacheable(cacheNames = [importCacheName ], key = "#impKey")
    override fun getAccessToken(impKey: String, impSecret: String): PortoneAccessTokenResponse {
        val portonePaymentResponse: PortoneAccessTokenResponse = portoneFeignClient.getAccessToken(impKey, impSecret)

        logger.info("getAccessToken(), portoneResponse:$portonePaymentResponse")

        portonePaymentResponse.verifyResponse()

        return portonePaymentResponse
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
        val portonePaymentResponse: PortonePaymentResponse = portoneFeignClient.payment(accessToken, merchantUid, price, cardNumber, expiry, birth, cardPassword, productName)

        logger.info("payment(), portoneResponse:$portonePaymentResponse")

        portonePaymentResponse.verifyResponse()

        return portonePaymentResponse
    }

    override fun cancel(impUid: String, merchantUid: String, price: Int): PortonePaymentResponse {
        val portonePaymentResponse: PortonePaymentResponse = portoneFeignClient.cancel(impUid, merchantUid, price)

        logger.info("cancel(), portoneResponse:$portonePaymentResponse")

        portonePaymentResponse.verifyResponse()

        return portonePaymentResponse
    }
}