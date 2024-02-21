package rlc.webtoon.api.auth.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.common.ApiError
import rlc.webtoon.api.common.Error
import rlc.webtoon.api.config.JwtProperties
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import java.util.Date

@Service
@Transactional(readOnly = true)
class AuthService(
        private val jwtService: JwtService,
        private val jwtProperties: JwtProperties,
        private val userRepository: UserRepository
) {

    @Transactional
    fun refreshToken(accountId: String, refreshToken: String): String {

        val user: User = userRepository.findByAccountId(accountId)
        val token: String = user.tokens.first { it.refreshToken == refreshToken }.refreshToken

        val extractedAccountId: String = jwtService.extractAccountId(token)

        if (jwtService.isValid(token) && extractedAccountId == accountId) {

            val newToken: String = createRefreshToken(accountId)
            user.addToken(newToken)

            return newToken
        }

        throw ApiError(Error.INVALID_JWT)

    }

    fun createRefreshToken(accountId: String) =
            jwtService.generate(accountId, getRefreshTokenExpiration())

    fun createAccessToken(accountId: String) =
            jwtService.generate(accountId, getAccessTokenExpiration())

    private fun getRefreshTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)

    private fun getAccessTokenExpiration(): Date =
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

}