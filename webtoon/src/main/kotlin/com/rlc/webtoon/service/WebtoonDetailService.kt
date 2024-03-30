package com.rlc.webtoon.service

import com.rlc.webtoon.dto.enumerated.PaymentType
import com.rlc.webtoon.dto.request.WebtoonDetailRequestDto
import com.rlc.webtoon.dto.response.WebtoonEpisodeResponse
import com.rlc.webtoon.dto.response.WebtoonPaymentResponse
import com.rlc.webtoon.entity.OpenType
import com.rlc.webtoon.entity.User
import com.rlc.webtoon.entity.WebtoonDetail
import com.rlc.webtoon.entity.WebtoonPaymentHistory
import com.rlc.webtoon.exception.RlcClientException
import com.rlc.webtoon.repostiory.UserRepository
import com.rlc.webtoon.repostiory.WebtoonDetailRepository
import com.rlc.webtoon.repostiory.WebtoonPaymentHistoryRepository
import com.rlc.webtoon.repostiory.WebtoonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional(readOnly = true)
class WebtoonDetailService(
    private val webtoonRepository: WebtoonRepository,
    private val webtoonDetailRepository: WebtoonDetailRepository,
    private val userRepository: UserRepository,
    private val webtoonPaymentHistoryRepository: WebtoonPaymentHistoryRepository,
) {

    @Transactional
    fun saveWebtoonDetail(webtoonId: Long, webtoonDetailRequestDto: WebtoonDetailRequestDto) {
        val webtoon = webtoonRepository.findById(webtoonId).getOrElse {
            throw RlcClientException("등록되어 있는 웹툰이 없습니다. $webtoonId")
        }

        val webtoonDetail = webtoonDetailRequestDto.toEntity().apply {
            this.webtoon = webtoon
        }

        webtoonDetailRepository.save(webtoonDetail)
    }

    /**
     * 웹툰 상세 조회.
     * OpenType.LOCK 이면 웹툰 결제 내역 조회 후 반환.
     */
    @Transactional
    fun viewWebtoonDetail(webtoonUuid: String, userUuid: String): WebtoonEpisodeResponse {
        val webtoon: WebtoonDetail = findWebtoonDetail(webtoonUuid)

        //현재 비공개 웹툰인 경우, 웹툰 결제 내역에서 결제한 내역 확인.
        if (webtoon.openType == OpenType.LOCK) {
            val paidWebtoonHistory =
                webtoonPaymentHistoryRepository.findByUserUuidAndWebtoonDetailUuidAndViewPossibleDateGreaterThan(
                    userUuid,
                    webtoonUuid,
                    Instant.now()
                ) ?: throw RlcClientException("결제가 필요한 웹툰입니다.")

            paidWebtoonHistory.viewFlag = true
        }

        return webtoon.toDetailResponseDto()
    }

    @Transactional
    fun payWebtoonDetail(webtoonUuid: String, userUuid: String): WebtoonPaymentResponse {
        val webtoon: WebtoonDetail = findWebtoonDetail(webtoonUuid)

        //현재 시각이 웹툰 공개일자를 지난 시점이면 바로 웹툰 리턴.
        if (webtoon.openType == OpenType.OPEN) {
            throw RlcClientException("무료 공개된 웹툰입니다.")
        }

        val user: User = userRepository.findById(userUuid).getOrElse {
            throw RlcClientException("등록되어 있는 유저가 없습니다.")
        }

        user.payPoint(webtoon.fee)
        val webtoonPaymentHistory: WebtoonPaymentHistory = webtoonPaymentHistoryRepository.save(
            WebtoonPaymentHistory.createWebtoonPaymentHistory(user, webtoon, webtoon.fee)
        )

        return webtoonPaymentHistory.createWebtoonPaymentResponse()
    }

    @Transactional
    fun refundPaidWebtoon(userUuid: String, webtoonPaymentHistoryId: Long): WebtoonPaymentResponse {
        val webtoonPaymentHistory: WebtoonPaymentHistory =
            webtoonPaymentHistoryRepository.findByIdAndUserUuid(
                webtoonPaymentHistoryId,
                userUuid
            ) ?: throw RlcClientException("환불가능한 결제정보가 없습니다.")

        if (webtoonPaymentHistory.viewFlag) {
            throw RlcClientException("이미 조회한 웹툰은 환불이 불가능합니다.")
        }
        if (webtoonPaymentHistory.viewPossibleDate < Instant.now()) {
            throw RlcClientException("환불 가능한 기간이 지났습니다.")
        }

        webtoonPaymentHistory.paymentType = PaymentType.CANCEL
        userRepository.findById(userUuid)
            .get()
            .apply {
                this.point += (webtoonPaymentHistory.fee * 0.6).toInt()
            }

        return webtoonPaymentHistory.createWebtoonPaymentResponse()
    }

    private fun findWebtoonDetail(webtoonUuid: String): WebtoonDetail {
        val webtoon: WebtoonDetail = webtoonDetailRepository.findById(webtoonUuid).getOrElse {
            throw RlcClientException("등록되어 있는 웹툰이 없습니다.")
        }
        return webtoon
    }

}