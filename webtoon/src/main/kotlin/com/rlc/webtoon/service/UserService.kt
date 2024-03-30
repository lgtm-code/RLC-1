package com.rlc.webtoon.service

import com.rlc.webtoon.dto.request.UserRequestDto
import com.rlc.webtoon.dto.response.UserResponseDto
import com.rlc.webtoon.exception.RlcClientException
import com.rlc.webtoon.repostiory.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 사용자 등록.
     */
    @Transactional
    fun signupUser(userRequestDto: UserRequestDto): UserResponseDto {
        isDuplicatedLoginId(userRequestDto.id)

        val user = userRequestDto.toEntity().apply {
            this.password = BCryptPasswordEncoder().encode(this.password)
        }

        log.info("signupUser(), user:$user")

        return UserResponseDto.of(userRepository.save(user))
    }

    /**
     * 사용자 로그인 아이디 중복 검사.
     * @exception RlcClientException 동일한 아이디가 있으면 발생.
     */
    private fun isDuplicatedLoginId(loginId: String) {
        if (userRepository.existsByLoginId(loginId)) {
            throw RlcClientException("이미 등록된 사용자 아이디가 있습니다. id:${loginId}")
        }
    }
}