package rlc.webtoon.api.user.application

import jakarta.persistence.EntityNotFoundException
import org.apache.coyote.BadRequestException
import org.springframework.dao.DuplicateKeyException
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import rlc.webtoon.api.user.presentation.dto.LoginRequest
import rlc.webtoon.api.user.presentation.dto.LoginResponse
import rlc.webtoon.api.user.presentation.dto.SignUpRequest
import java.util.Base64.Encoder


@Transactional(readOnly = true)
class UserService(
        private val authService: AuthService,
        private val userRepository: UserRepository
) {

    fun signUp(request: SignUpRequest) {

        checkDuplicateAccountId(request.accountId)

        userRepository.save(request.toUser())
    }

    fun login(request: LoginRequest): LoginResponse {
        val user: User = userRepository.findByAccountId(request.accountId)
                ?: throw EntityNotFoundException("존재하는 사용자가 없습니다.")

        matchPassword(user, request)

        val accessToken: String = authService.createAccessToken(user.accountId)
        val refreshToken: String = authService.createRefreshToken(user.accountId)

        return LoginResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
        )
    }

    private fun checkDuplicateAccountId(accountId: String) {
        val user: User? = userRepository.findByAccountId(accountId)

        if (user != null) {
            throw DuplicateKeyException("이미 존재하는 사용자 id입니다.")
        }

    }

    private fun matchPassword(user: User, request: LoginRequest) {
        if (user.password != request.password) {
            throw BadRequestException("비밀번호가 일치하지 않습니다.")
        }
    }

}