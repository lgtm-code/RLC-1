package rlc.webtoon.api.auth.presentation

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.application.JwtService
import rlc.webtoon.api.common.ApiError
import rlc.webtoon.api.common.Error

@Configuration
class JwtArgumentResolver(
        private val jwtService: JwtService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        
        if (parameter.getParameterAnnotation(Authenticated::class.java) == null) {
            return false
        }

        return parameter.parameterType == UserAuth::class.java
    }

    override fun resolveArgument(parameter: MethodParameter,
                                 mavContainer: ModelAndViewContainer?,
                                 webRequest: NativeWebRequest,
                                 binderFactory: WebDataBinderFactory?): Any {

        val request: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest

        val jwt: String = getJwt(request)

        if (jwtService.isValid(jwt)) {
            val accountId: String = jwtService.extractAccountId(jwt)
            return UserAuth(accountId)
        }

        throw ApiError(Error.INVALID_JWT)
    }

    private fun getJwt(request: HttpServletRequest): String {

        val authorization: String? = request.getHeader("Authorization")

        return authorization?.takeIf { it.startsWith("Bearer ") }?.substring(7) ?: ""
    }

}