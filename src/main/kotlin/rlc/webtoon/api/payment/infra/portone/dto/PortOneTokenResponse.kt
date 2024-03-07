package rlc.webtoon.api.payment.infra.portone.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class PortOneTokenResponse(
        val response: PortOneTokenAnnotation
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PortOneTokenAnnotation(
        val accessToken: String,
        val now: Long,
        val expiredAt: Long
)