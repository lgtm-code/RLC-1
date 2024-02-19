package rlc.webtoon.api.auth.application

import org.springframework.stereotype.Service
import rlc.webtoon.api.config.JwtProperties
import java.util.Date

@Service
class AuthService(
        private val jwtService: JwtService,
        private val jwtProperties: JwtProperties
) {

     fun createRefreshToken(accountId: String) =
            jwtService.generate(accountId, getRefreshTokenExpiration())

     fun createAccessToken(accountId: String) =
            jwtService.generate(accountId, getAccessTokenExpiration())

    private fun getRefreshTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

    private fun getAccessTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

}