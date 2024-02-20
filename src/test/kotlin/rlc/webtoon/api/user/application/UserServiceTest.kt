package rlc.webtoon.api.user.application

import org.apache.coyote.BadRequestException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import rlc.webtoon.api.common.PasswordEncoder
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import rlc.webtoon.api.user.infra.UserTokenRepository
import rlc.webtoon.api.user.presentation.dto.LoginRequest
import rlc.webtoon.api.user.presentation.dto.LoginResponse
import rlc.webtoon.api.user.presentation.dto.SignUpRequest
import java.lang.IllegalArgumentException

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserServiceTest @Autowired constructor(
        val userService: UserService,
        val userRepository: UserRepository,
        val userTokenRepository: UserTokenRepository
) {

    @AfterEach
    fun tearDown() {
        userTokenRepository.deleteAllInBatch()
        userRepository.deleteAllInBatch()
    }

    @Order(1)
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

    @Order(2)
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

    @Order(3)
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
        val response: LoginResponse = userService.login(loginRequest)

        // then
        assertNotNull(response.accessToken)
        assertNotNull(response.refreshToken)
    }


    @Order(4)
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
            userService.login(loginRequest)
        }

        assertEquals(exception.message, "비밀번호가 일치하지 않습니다.")
    }
}