package rlc.webtoon.api.user.application

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import rlc.webtoon.api.user.infra.UserTokenRepository
import rlc.webtoon.api.user.presentation.dto.LoginRequest
import rlc.webtoon.api.user.presentation.dto.LoginResponse
import rlc.webtoon.api.user.presentation.dto.SignUpRequest

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserServiceTest @Autowired constructor(
        val authService: AuthService,
        val userService: UserService,
        val userRepository: UserRepository,
        val userTokenRepository: UserTokenRepository
) {

    @AfterEach
    fun tearDown() {
        userTokenRepository.deleteAllInBatch()
        userRepository.deleteAllInBatch()
    }

    @Test
    @DisplayName("사용자는 중복되지 않은 아이디와 비밀번호로 회원가입 한다.")
    fun signUp() {
        // given
        val accountId = "testUser1"
        val request = SignUpRequest(
                accountId = accountId,
                password = "123"
        )

        // when
        userService.signUp(request)

        // then
        val user: User = userRepository.findByAccountId(accountId)
        assertEquals(accountId, user.accountId)
    }

    @Test
    @DisplayName("사용자가 중복된 아이디로 회원가입 할 경우 예외가 발생한다.")
    fun signUpWithDuplicatedAccountId() {
        // given
        val accountId = "testUser1"

        val request = SignUpRequest(
                accountId = accountId,
                password = "123"
        )

        val user = User(
                accountId = accountId,
                password = "123"
        )

        userRepository.save(user)

        // when then
        val exception = assertThrows(DuplicateKeyException::class.java) {
            userService.signUp(request)
        }

        assertEquals(exception.message, "이미 존재하는 사용자입니다.")
    }

    @Test
    @DisplayName("사용자는 올바른 아이디와 비밀번호로 로그인 한다.")
    fun login() {
        // given
        val accountId = "test123"
        val password = "123"

        val signUpRequest = SignUpRequest(
                accountId = accountId,
                password = password
        )

        val loginRequest = LoginRequest(
                accountId = accountId,
                password = password
        )

        userService.signUp(signUpRequest)

        // when
        val response: LoginResponse = authService.login(loginRequest)

        // then
        assertNotNull(response.accessToken)
        assertNotNull(response.refreshToken)
    }


    @Test
    @DisplayName("사용자는 올바르지 않은 비밀번호로 로그인 할 경우 예외가 발생한다.")
    fun loginWithInvalidPassword() {
        // given
        val accountId = "test123"
        val password = "123"

        val signUpRequest = SignUpRequest(
                accountId = accountId,
                password = password
        )

        val loginRequest = LoginRequest(
                accountId = accountId,
                password = "wrongpassword123"
        )

        userService.signUp(signUpRequest)

        // when then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            authService.login(loginRequest)
        }

        assertEquals(exception.message, "비밀번호가 일치하지 않습니다.")
    }
}