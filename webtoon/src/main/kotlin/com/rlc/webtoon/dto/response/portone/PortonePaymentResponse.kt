package com.rlc.webtoon.dto.response.portone

import com.fasterxml.jackson.annotation.JsonProperty

data class PortonePaymentResponse(
    val response: PortoneResponseData?
): PortoneCommonResponse(){

    companion object {
        fun successResponseFixture(): PortonePaymentResponse {
            return PortonePaymentResponse(
                response = null
            )
        }
    }

    override fun toString(): String {
        return "PortonePaymentResponse(code:$code, message:$message, response=$response)"
    }
}

data class PortoneResponseData(
    val amount: Int,
    @JsonProperty("apply_num")
    val applyNum: String,
    @JsonProperty("bank_code")
    val bankCode: String?,
    @JsonProperty("bank_name")
    val bankName: String?,
    @JsonProperty("buyer_addr")
    val buyerAddr: String?,
    @JsonProperty("buyer_email")
    val buyerEmail: String?,
    @JsonProperty("buyer_name")
    val buyerName: String?,
    @JsonProperty("buyer_postcode")
    val buyerPostcode: String?,
    @JsonProperty("buyer_tel")
    val buyerTel: String?,
    @JsonProperty("cancel_amount")
    val cancelAmount: Int,
    @JsonProperty("cancel_history")
    val cancelHistory: List<CancelHistory>,
    @JsonProperty("cancel_reason")
    val cancelReason: String?,
    @JsonProperty("cancel_receipt_urls")
    val cancelReceiptUrls: List<String>,
    @JsonProperty("cancelled_at")
    val cancelledAt: Long,
    @JsonProperty("card_code")
    val cardCode: String,
    @JsonProperty("card_name")
    val cardName: String,
    @JsonProperty("card_number")
    val cardNumber: String,
    @JsonProperty("card_quota")
    val cardQuota: Int,
    @JsonProperty("card_type")
    val cardType: Int,
    @JsonProperty("cash_receipt_issued")
    val cashReceiptIssued: Boolean,
    val channel: String,
    val currency: String,
    @JsonProperty("custom_data")
    val customData: String?,
    @JsonProperty("customer_uid")
    val customerUid: String?,
    @JsonProperty("customer_uid_usage")
    val customerUidUsage: String?,
    @JsonProperty("emb_pg_provider")
    val embPgProvider: String?,
    val escrow: Boolean,
    @JsonProperty("fail_reason")
    val failReason: String?,
    @JsonProperty("failed_at")
    val failedAt: Long,
    @JsonProperty("imp_uid")
    val impUid: String,
    @JsonProperty("merchant_uid")
    val merchantUid: String,
    val name: String,
    @JsonProperty("paid_at")
    val paidAt: Long,
    @JsonProperty("pay_method")
    val payMethod: String,
    @JsonProperty("pg_id")
    val pgId: String,
    @JsonProperty("pg_provider")
    val pgProvider: String,
    @JsonProperty("pg_tid")
    val pgTid: String,
    @JsonProperty("receipt_url")
    val receiptUrl: String,
    @JsonProperty("started_at")
    val startedAt: Long,
    val status: String,
    @JsonProperty("user_agent")
    val userAgent: String,
    @JsonProperty("vbank_code")
    val vbankCode: String?,
    @JsonProperty("vbank_date")
    val vbankDate: Long,
    @JsonProperty("vbank_holder")
    val vbankHolder: String?,
    @JsonProperty("vbank_issued_at")
    val vbankIssuedAt: Long,
    @JsonProperty("vbank_name")
    val vbankName: String?,
    @JsonProperty("vbank_num")
    val vbankNum: String?
)

data class CancelHistory(
    @JsonProperty("pg_tid")
    val pgTid: String,
    val amount: Int,
    @JsonProperty("cancelled_at")
    val cancelledAt: Long,
    val reason: String,
    @JsonProperty("receipt_url")
    val receiptUrl: String,
    @JsonProperty("cancellation_id")
    val cancellationId: String
)