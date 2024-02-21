package rlc.webtoon.api.common

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {

    fun bcrypt(password: String): String {
        if(password.isBlank()){
            throw ApiError(Error.BLANK_PASSWORD)
        }
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

    fun match(rawPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword, hashedPassword)
    }
}