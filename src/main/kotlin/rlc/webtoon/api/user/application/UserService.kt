package rlc.webtoon.api.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.common.ApiError
import rlc.webtoon.api.common.Error
import rlc.webtoon.api.common.PasswordEncoder
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import rlc.webtoon.api.user.presentation.dto.LoginRequest
import rlc.webtoon.api.user.presentation.dto.LoginResponse
import rlc.webtoon.api.user.presentation.dto.SignUpRequest


@Service
@Transactional(readOnly = true)
class UserService(
        private val authService: AuthService,
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signUp(request: SignUpRequest) {

        checkDuplicateAccountId(request.accountId)

        val hashedPassword: String = passwordEncoder.bcrypt(request.password)

        userRepository.save(request.toUser(hashedPassword))
    }

    @Transactional
    fun login(request: LoginRequest): LoginResponse {
        val user: User = userRepository.findByAccountId(request.accountId)

        matchPassword(
                rawPassword = request.password,
                hashedPassword = user.password
        )

        val accessToken: String = authService.createAccessToken(user.accountId)
        val refreshToken: String = authService.createRefreshToken(user.accountId)

        user.addToken(refreshToken)

        return LoginResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
        )
    }

    private fun checkDuplicateAccountId(accountId: String) {

        val isDuplicated: Boolean = userRepository.existsUserByAccountId(accountId)

        if (isDuplicated) {
            throw ApiError(Error.DUPLICATED_ACCOUNT_ID)
        }

    }

    private fun matchPassword(rawPassword: String, hashedPassword: String) {

        if (!passwordEncoder.match(rawPassword, hashedPassword)) {
            throw ApiError(Error.INVALID_PASSWORD)
        }

    }

}