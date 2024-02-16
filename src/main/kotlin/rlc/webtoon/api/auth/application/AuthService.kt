package rlc.webtoon.api.auth.application

import org.springframework.stereotype.Service
import rlc.webtoon.api.config.JwtProperties
import java.util.Date

@Service
class AuthService(
        private val jwtProperties: JwtProperties,
        private val jwtService: JwtService
) {

    private fun createRefreshToken(userId: String) =
            jwtService.generate(userId, getRefreshTokenExpiration())

    private fun createAccessToken(userId: String) =
            jwtService.generate(userId, getAccessTokenExpiration())

    private fun getRefreshTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

    private fun getAccessTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

}