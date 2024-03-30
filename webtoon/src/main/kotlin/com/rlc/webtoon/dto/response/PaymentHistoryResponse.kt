package com.rlc.webtoon.dto.response

data class PaymentHistoryResponse(
    val productName: String,
    val price: Int, //결제 금액
    val userPoint: Int //사용자 소지 포인트(잎)
){

    companion object {
        fun create(productName: String, price: Int, userPoint: Int) : PaymentHistoryResponse {
            return PaymentHistoryResponse(
                productName = productName,
                price = price,
                userPoint = userPoint
            )
        }
    }
}

