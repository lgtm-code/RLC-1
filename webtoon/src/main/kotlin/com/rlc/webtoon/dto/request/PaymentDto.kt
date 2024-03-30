package com.rlc.webtoon.dto.request

import com.rlc.webtoon.dto.enumerated.ProductType

data class PaymentDto(
    val cardNumber: String, //카드 번호 dddd-dddd-dddd-dddd
    val expiry: String, //카드 유효기간 YYYY-MM
    val birth: String, //사용자 생년월일 YYMMDD,
    val cardPassword: String, // 카드 비밀번호 앞 2자리
    val productType: ProductType, // 상품 결제명
) {

    companion object {
        fun fixture(): PaymentDto {
            return PaymentDto(
                cardNumber = "0000-0000-0000-0000",
                expiry = "2024-12",
                birth = "000000",
                cardPassword = "00",
                productType = ProductType.MIDDLE
            )
        }
    }
}