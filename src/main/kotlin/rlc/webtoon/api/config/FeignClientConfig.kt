package rlc.webtoon.api.config

import feign.Retryer
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

@Configuration
@ImportAutoConfiguration(FeignAutoConfiguration::class)
@EnableFeignClients(value = ["rlc.webtoon.api"])
class FeignClientConfig {

    @Bean
    fun retry(): Retryer {
        return Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5)
    }
}