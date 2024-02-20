package rlc.webtoon.api.auth.application

import io.jsonwebtoken.Claims
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import io.jsonwebtoken.Jwts
import rlc.webtoon.api.config.JwtProperties
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
        jwtProperties: JwtProperties
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
            jwtProperties.key.toByteArray()
    )

    fun generate(
            accountId: String,
            expiration: Date,
            additionalClaims: Map<String, Any> = emptyMap()
    ): String =
            Jwts.builder()
                    .subject(accountId)
                    .claims()
                    .issuedAt(Date(System.currentTimeMillis()))
                    .expiration(expiration)
                    .add(additionalClaims)
                    .and()
                    .signWith(secretKey)
                    .compact()

    fun isValid(
            jwt: String,
            accountId: String
    ): Boolean {
        val extractedUserId: String = extractUserId(jwt)

        return accountId == extractedUserId && !isExpired(jwt)
    }


    private fun extractUserId(jwt: String): String =
            getAllClaims(jwt)
                    .subject


    private fun isExpired(jwt: String): Boolean =
            getAllClaims(jwt)
                    .expiration
                    .before(Date(System.currentTimeMillis()))


    private fun getAllClaims(jwt: String): Claims {
        val parser = Jwts.parser()
                .verifyWith(secretKey)
                .build()

        return parser
                .parseSignedClaims(jwt)
                .payload
    }

}