package com.rlc.webtoon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class]) //SpringSecurity 관련 인증/인가를 적용하지 않기때문에 exclude
@EnableJpaAuditing
class WebtoonApplication

fun main(args: Array<String>) {
	runApplication<WebtoonApplication>(*args)
}