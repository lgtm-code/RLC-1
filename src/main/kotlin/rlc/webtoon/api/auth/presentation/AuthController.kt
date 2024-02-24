package rlc.webtoon.api.auth.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.application.AuthService
import rlc.webtoon.api.common.client.ApiResponse
import rlc.webtoon.api.user.presentation.dto.LoginRequest
import rlc.webtoon.api.user.presentation.dto.LoginResponse

@Tag(name = "인증")
@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService
) {
    @PostMapping("/refresh")
    fun refreshToken(@Authenticated userAuth: UserAuth,
                     @RequestHeader refreshToken: String): ApiResponse<String> {
        val newRefreshToken: String = authService.refreshToken(userAuth.accountId, refreshToken)
        return ApiResponse(newRefreshToken)
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse> {
        return ApiResponse(authService.login(request))
    }
}