package rlc.webtoon.api.common.client

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class ApiError(val error: Error, customMessage: String? = null) : RuntimeException(customMessage ?: error.message)

enum class Error(val status: HttpStatus, val message: String = "") {
    DUPLICATED_ACCOUNT_ID(HttpStatus.ALREADY_REPORTED, "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    BLANK_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호 공백 불가합니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자 입니다.")

}