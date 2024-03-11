package com.rlc.webtoon.entity

import com.rlc.webtoon.dto.enumerated.PaymentType
import com.rlc.webtoon.dto.enumerated.ProductType
import com.rlc.webtoon.entity.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "tb_payment_histories")
class PaymentHistory constructor(
    @Transient
    val productType: ProductType,

    val impUid: String?, //포트원 거래고유번호 (결제 승인)
    val merchantUid: String?, //주문번호
    val cardName: String?,
    var cardNumber: String?,

    @ManyToOne
    val user: User,

    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {

    val price: Int = productType.price

    val productName: String = productType.productName

    init {
        if (cardNumber != null) {
            cardNumber = maskingCardNumber(this.cardNumber!!)
        }
    }

    /**
     * 카드번호 마스킹
     */
    private fun maskingCardNumber(cardNumber: String): String {
        val maskingNumber = 4
        val maskedChars = cardNumber.length.minus(4)

        return "${cardNumber.take(maskingNumber)}${"x".repeat(maskedChars)}${cardNumber.takeLast(maskingNumber)}"
    }

    companion object {
        fun create(
            productType: ProductType,
            impUid: String,
            merchantUid: String,
            cardName: String,
            cardNumber: String,
            user: User,
            paymentType: PaymentType
        ): PaymentHistory {
            return PaymentHistory(
                productType, impUid, merchantUid, cardName, cardNumber, user, paymentType
            )
        }
    }

}



