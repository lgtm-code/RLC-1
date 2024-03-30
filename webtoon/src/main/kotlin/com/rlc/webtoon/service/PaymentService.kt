package com.rlc.webtoon.service

import com.rlc.webtoon.dto.enumerated.PaymentType
import com.rlc.webtoon.dto.request.PaymentDto
import com.rlc.webtoon.dto.response.PaymentHistoryResponse
import com.rlc.webtoon.dto.response.portone.PortoneAccessTokenResponse
import com.rlc.webtoon.dto.response.portone.PortoneResponseData
import com.rlc.webtoon.entity.PaymentHistory
import com.rlc.webtoon.entity.User
import com.rlc.webtoon.exception.RlcClientException
import com.rlc.webtoon.repostiory.PaymentHistoryRepository
import com.rlc.webtoon.repostiory.UserRepository
import com.rlc.webtoon.service.inter.PortoneInterface
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val portoneInterface: PortoneInterface,
    private val userRepository: UserRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${import.key}")
    private lateinit var importKey: String

    @Value("\${import.secret}")
    private lateinit var importSecret: String

    private val bearer: String = "Bearer"

    @Transactional
    fun payment(userUuid: String, paymentDto: PaymentDto): PaymentHistoryResponse {
        logger.info("payment(), userUuid:$userUuid, paymentDto:$paymentDto")

        val user = userRepository.findById(userUuid).getOrElse {
            throw RlcClientException("사용자를 찾을 수 없습니다. uuid:$userUuid")
        }

        val responseData: PortoneResponseData? = portoneInterface.payment(
            accessToken = getAccessToken(),
            merchantUid = UUID.randomUUID().toString(),
            price = paymentDto.productType.price,
            cardNumber =  paymentDto.cardNumber,
            expiry = paymentDto.expiry,
            birth = paymentDto.birth,
            cardPassword = paymentDto.cardPassword,
            productName = paymentDto.productType.productName
        ).response

        if (responseData != null) {
            savePaymentInfo(paymentDto, responseData, user, PaymentType.PAY)
        }

        return PaymentHistoryResponse.create(
            paymentDto.productType.productName,
            paymentDto.productType.price,
            user.point
        )
    }

    /**
     * 결제 정보 DB 저장.
     */
    private fun savePaymentInfo(
        paymentDto: PaymentDto,
        responseData: PortoneResponseData,
        user: User,
        paymentType: PaymentType
    ) {
        paymentHistoryRepository.save(
            PaymentHistory.create(
                productType = paymentDto.productType,
                impUid = responseData.impUid,
                merchantUid = responseData.merchantUid,
                cardName = responseData.cardName,
                cardNumber = responseData.cardNumber,
                user = user,
                paymentType = paymentType
            )
        )

        user.point += responseData.amount
    }

    /**
     * portOne accessToken 발급.
     */
    private fun getAccessToken(): String {
        val portoneResponse: PortoneAccessTokenResponse = portoneInterface.getAccessToken(importKey, importSecret)

        portoneResponse.verifyResponse()

        return "$bearer ${portoneResponse.response.accessToken}"
    }
}