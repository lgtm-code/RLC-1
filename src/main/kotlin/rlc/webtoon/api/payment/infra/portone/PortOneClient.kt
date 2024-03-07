package rlc.webtoon.api.payment.infra.portone

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import rlc.webtoon.api.payment.infra.portone.dto.*

@FeignClient(
        name = "PortOneClient",
        url = "https://api.iamport.kr"
)
interface PortOneClient {
    @PostMapping("/users/getToken")
    fun getToken(@RequestBody body: RequestGetToken): PortOneTokenResponse

    @PostMapping("/subscribe/payments/onetime")
    fun pay(
            @RequestHeader authorization: String,
            @RequestBody body: RequestPaymentOneTime
    ): PortOnePaymentResponse
    @PostMapping("/payments/cancel")
    fun cancel(
            @RequestHeader authorization: String,
            @RequestBody body: RequestCancelPayment
    ): PortOnePaymentResponse
}


