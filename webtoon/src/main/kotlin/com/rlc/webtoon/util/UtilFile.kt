package com.rlc.webtoon.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.http.HttpServletRequest

val jsonMapper = ObjectMapper().registerKotlinModule() //jacksonMapper
val paidWebtoonViewDate: Long = 60*60*24*2 //웹툰 결제 후 조회 가능 시간 (2일)

const val accessToken = "accessToken"
const val refreshToken = "refreshToken"
const val userUuid = "userUuid"

const val importCacheName = "importAccessToken"

fun HttpServletRequest.userUuid(): String {
    return this.getAttribute(userUuid).toString()
}