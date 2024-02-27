package rlc.webtoon.api.user.presentation.dto

import rlc.webtoon.api.user.domain.User

data class SignUpRequest(
        val accountId: String,
        val password: String
) {
    fun toUser(hashPassword:String): User = User(
            accountId = accountId,
            password = hashPassword
    )
}