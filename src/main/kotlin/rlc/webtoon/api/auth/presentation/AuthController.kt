package rlc.webtoon.api.auth.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.common.ApiResponse

@Tag(name = "인증")
@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService
) {
    @PostMapping("/refresh")
    fun refreshToken(@Authenticated userAuth: UserAuth,
                     @RequestParam refreshToken: String): ApiResponse<String> {
        val newRefreshToken: String = authService.refreshToken(userAuth.accountId, refreshToken)
        return ApiResponse(newRefreshToken)
    }
}