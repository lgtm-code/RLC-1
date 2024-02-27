package rlc.webtoon.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import rlc.webtoon.api.auth.presentation.JwtArgumentResolver


@Configuration
class ArgumentConfig(
        private val jwtArgumentResolver: JwtArgumentResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtArgumentResolver)
    }
}