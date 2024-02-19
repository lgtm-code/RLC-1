package rlc.webtoon.api.user.presentation.dto

data class LoginResponse(
        val accessToken: String,
        val refreshToken: String
)