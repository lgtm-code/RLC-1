package rlc.webtoon.api.user.presentation.dto

data class LoginRequest(
        val accountId: String,
        val password: String
)