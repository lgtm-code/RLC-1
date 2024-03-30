package com.rlc.webtoon.config

import com.github.benmanes.caffeine.cache.Caffeine
import com.rlc.webtoon.util.importCacheName
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Arrays
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

@Configuration
class CaffeineCacheConfig {

    /**
     * cache 이름, 만료시간 설정.
     */
    @Bean
    fun caffeineCacheManager(): CacheManager {
        val caffeineCaches = Arrays.stream(CacheType.entries.toTypedArray()).map { cacheType ->
            CaffeineCache(
                cacheType.cacheName, Caffeine.newBuilder()
                    .expireAfterWrite(cacheType.expiredTime, TimeUnit.MINUTES)
                    .build()
            )
        }.collect(Collectors.toList())

        return SimpleCacheManager().apply {
            setCaches(caffeineCaches)
        }

    }
}

enum class CacheType(val cacheName: String, val expiredTime: Long) {
    IMPORT_ACCESS_TOKEN(importCacheName, 30) //만료시간 30min
}