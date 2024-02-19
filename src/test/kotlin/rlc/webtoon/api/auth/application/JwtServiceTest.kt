package rlc.webtoon.api.auth.application

import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Date

@SpringBootTest
class JwtServiceTest @Autowired constructor(
        val jwtService: JwtService
) {
    companion object {
        const val ACCOUNT_ID = "testUser1"
    }

    @Test
    @DisplayName("JWT를 생성한다.")
    fun generate() {
        // given
        val jwt = generateJWT(1000)

        // when then
        assertNotNull(jwt)
    }

    @Test
    @DisplayName("유효한 JWT를 사용한다.")
    fun validateJWT() {
        // given
        val jwt = generateJWT(1000)

        // when then
        assertTrue(jwtService.isValid(jwt, ACCOUNT_ID))
    }

    @Test
    @DisplayName("유효하지않은 JWT를 사용시 ExpiredJwtException 예외가 발생한다.")
    fun invalidateJWT() {
        // given
        val jwt = generateJWT(-200)

        // when then
        assertThrows(ExpiredJwtException::class.java) {
            jwtService.isValid(jwt, ACCOUNT_ID)
        }
    }


    private fun generateJWT(milliseconds: Int): String {
        val expiration = Date(System.currentTimeMillis() + milliseconds)

        return jwtService.generate(ACCOUNT_ID, expiration)
    }
}