package rlc.webtoon.api.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.common.client.ApiError
import rlc.webtoon.api.common.client.Error
import rlc.webtoon.api.common.util.PasswordEncoder
import rlc.webtoon.api.user.infra.UserRepository
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

    private fun checkDuplicateAccountId(accountId: String) {

        val isDuplicated: Boolean = userRepository.existsUserByAccountId(accountId)

        if (isDuplicated) {
            throw ApiError(Error.DUPLICATED_ACCOUNT_ID)
        }

    }


}