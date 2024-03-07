package rlc.webtoon.api.payment.infra.portone

import org.springframework.stereotype.Service
import rlc.webtoon.api.config.PortOneProperties
import rlc.webtoon.api.payment.infra.portone.dto.PortOnePaymentResponse
import rlc.webtoon.api.payment.infra.portone.dto.RequestCancelPayment
import rlc.webtoon.api.payment.infra.portone.dto.RequestGetToken
import rlc.webtoon.api.payment.infra.portone.dto.RequestPaymentOneTime
import rlc.webtoon.api.payment.presentation.dto.PayCard
import java.util.*


@Service
class PortOneServiceImpl(
        private val client: PortOneClient,
        private val properties: PortOneProperties
) : PortOneService {
    override fun pay(paymentPurpose:String, card: PayCard, amount: Int): PortOnePaymentResponse {
        val bodyRequest = RequestPaymentOneTime(
                name = paymentPurpose,
                merchantUid = "kcd_${UUID.randomUUID()}",
                cardNumber = card.cardNumber,
                pwd2digit = card.pw2digit,
                expiry = card.expiry,
                birth = card.birth,
                amount = amount
        )

        return client.pay(
                authorization = getAccessToken(),
                body = bodyRequest
        )
    }

    override fun cancel(impUid: String, amount: Int, reason: String): PortOnePaymentResponse {
        return client.cancel(
                getAccessToken(),
                RequestCancelPayment(
                        impUid = impUid,
                        amount = amount,
                        reason = reason
                )
        )
    }

    private fun getAccessToken() =
            client.getToken(
                    RequestGetToken(
                            impKey = properties.key,
                            impSecret = properties.secret
                    )
            ).response.accessToken
}