package com.rlc.webtoon.dto.response.portone

import com.rlc.webtoon.exception.RlcServerException

abstract class PortoneCommonResponse(
    open val code: Int,
    open val message: String?
) {
    constructor() : this(0, null)

    /**
     * 포트원 응답코드 검증.
     * 0인 경우에 결제관련 요청이 성공적으로 수행된것으로 판단.
     * @throws RlcServerException code != 0 이면
     */
    fun verifyResponse() {
        if(code != 0) {
            throw RlcServerException(errorMessage = message.toString(), errorCode = code.toString())
        }
    }
}
