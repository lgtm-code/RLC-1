package rlc.webtoon.api.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringBootTest
class PasswordEncoderTest @Autowired constructor(
        private val passwordEncoder: PasswordEncoder
) {

    @Test
    @DisplayName("비밀번호를 bcrypt를 사용해 암호화 한다.")
    fun bcrypt() {
        // given
        val rawPassword = "test_123"

        // when
        val hashedPassword: String = passwordEncoder.bcrypt(rawPassword)

        // then
        assertNotNull(hashedPassword)
    }

    @Test
    @DisplayName("주어진 비밀번호와 해쉬된 비밀번호가 같은 값인지 확인한다.")
    fun match() {
        // given
        val rawPassword = "test_123"
        val hashedPassword = passwordEncoder.bcrypt(rawPassword)

        // when
        val isMatched: Boolean = passwordEncoder.match(rawPassword, hashedPassword)

        // then
        assertTrue(isMatched)
    }
}