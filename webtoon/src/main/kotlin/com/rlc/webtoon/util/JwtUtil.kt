package com.rlc.webtoon.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Component
class JwtUtil {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${jwt.secretKey}")
    private lateinit var secretKey: String

    @Value("\${jwt.validTokenDay}")
    private val validDay: Long = 0

    @Value("\${jwt.validTokenTime}")
    private val validTime: Long = 0

    /**
     * Jwt 생성.
     * accessToken 만료기간: 60분.
     * refreshToken 만료기간: 30일.
     */
    fun createJwt(uuid: String): Map<String, String> {
        val accessToken = createAccessToken(uuid)
        val refreshToken = createRefreshToken(uuid)

        return mapOf(
            com.rlc.webtoon.util.accessToken to accessToken,
            com.rlc.webtoon.util.refreshToken to refreshToken
        )
    }

    private fun createAccessToken(uuid: String): String {
        val accessTokenExpiryTime = Date.from(
            Instant.now()
                .plus(validTime, ChronoUnit.MINUTES)
        )

        return Jwts.builder()
            .signWith(createSignKey())
            .subject(uuid)
            .issuedAt(Date())
            .expiration(accessTokenExpiryTime)
            .compact()
    }

    private fun createRefreshToken(uuid: String): String {
        val refreshTokenExpiryTime = Date.from(
            Instant.now()
                .plus(validDay, ChronoUnit.DAYS)
        )

        return Jwts.builder()
            .signWith(createSignKey())
            .subject(uuid)
            .issuedAt(Date())
            .expiration(refreshTokenExpiryTime)
            .compact()
    }

    /**
     * @ref https://layerzroworld.com/?_=%2Fjwtk%2Fjjwt%2Fissues%2F915%23tjR7W8h0CmIExmRRbzxbaD%2FN
     */
    private fun createSignKey(): Key {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.toByteArray()))
    }

    /**
     * AccessToken 유효성 검증.
     * @throws JwtException accessToken 형식이 잘못되었거나 유효기간이 만료된 경우 발생
     */
    fun isValidAccessToken(accessToken: String): Boolean {
        try {
            getPayload(accessToken)
        }catch (e: JwtException) {
            log.error("isValidAccessToken(), jwtException accessToken:$accessToken", e)
            return false
        }

        return true
    }

    /**
     * refreshToken 유효성 검증.
     * @throws JwtException refreshToken 형식이 잘못되었거나 유효기간이 만료된 경우 발생
     */
    fun isValidRefreshToken(refreshToken: String): Boolean {
        try {
            getPayload(refreshToken)
        }catch (e: JwtException) {
            log.error("isValidRefreshToken(), jwtException refreshToken:$refreshToken", e)
            return false
        }

        return true
    }

    fun getPayload(accessToken: String): Claims {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.toByteArray())))
            .build()
            .parseSignedClaims(accessToken)
            .payload
    }

}