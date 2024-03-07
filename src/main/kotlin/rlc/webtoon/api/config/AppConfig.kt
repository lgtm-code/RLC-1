package rlc.webtoon.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
        JwtProperties::class,
        PortOneProperties::class
)
class AppConfig {
}

@ConfigurationProperties("jwt")
data class JwtProperties @ConstructorBinding constructor(
        val key: String,
        val accessTokenExpiration: Long,
        val refreshTokenExpiration: Long
)

@ConfigurationProperties(prefix = "portone")
data class PortOneProperties(
        val key: String,
        val secret: String
)