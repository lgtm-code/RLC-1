package com.rlc.webtoon.dto.response.portone

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class PortoneAccessTokenResponse(
    val response: AccessTokenResponse
): PortoneCommonResponse() {

    companion object {
        fun successResponseFixture(): PortoneAccessTokenResponse {
            return PortoneAccessTokenResponse(
                AccessTokenResponse(
                    accessToken = "testAccessToken",
                    now = Instant.now().toEpochMilli(),
                    expiredAt = Instant.now().toEpochMilli()
                )
            )
        }
    }
}

data class AccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    val now: Long,
    @JsonProperty("expired_at")
    val expiredAt: Long
)