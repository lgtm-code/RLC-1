package rlc.webtoon.api.payment.infra.portone.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RequestGetToken(
        val impKey: String,
        val impSecret: String
)
