package com.rlc.webtoon.entity

import com.rlc.webtoon.dto.enumerated.PaymentType
import com.rlc.webtoon.dto.response.WebtoonPaymentResponse
import com.rlc.webtoon.entity.common.BaseTimeEntity
import com.rlc.webtoon.util.paidWebtoonViewDate
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tb_webtoon_payment_history")
class WebtoonPaymentHistory(
    @OneToOne(fetch = FetchType.LAZY)
    val user: User,
    @OneToOne(fetch = FetchType.LAZY)
    val webtoonDetail: WebtoonDetail,
    val fee: Int, //결제 금액, 나중에 웹툰별로 결제 금액이 나뉘어질것 대비

    var viewFlag: Boolean = false, //조회 여부
    @Enumerated(EnumType.STRING)
    var paymentType: PaymentType = PaymentType.PAY,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
): BaseTimeEntity() {

    val viewPossibleDate: Instant = createdDate.plusSeconds(paidWebtoonViewDate)

    fun createWebtoonPaymentResponse(): WebtoonPaymentResponse {
        return WebtoonPaymentResponse(
            id!!,
            webtoonDetail.webtoon.title,
            webtoonDetail.episode,
            paymentType,
            fee,
            createdDate,
            updatedDate,
            viewPossibleDate
        )
    }

    companion object {
        fun createWebtoonPaymentHistory(
            user: User,
            webtoonDetail: WebtoonDetail,
            fee: Int
        ): WebtoonPaymentHistory {
            return WebtoonPaymentHistory(user, webtoonDetail, fee)
        }


    }
}