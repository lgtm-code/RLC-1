package com.rlc.webtoon.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.http.HttpServletRequest

val jsonMapper = ObjectMapper().registerKotlinModule() //jacksonMapper

const val accessToken = "accessToken"
const val refreshToken = "refreshToken"
const val userUuid = "userUuid"

const val importCacheName = "importAccessToken"

fun HttpServletRequest.userUuid(): String {
    return this.getAttribute(userUuid).toString()
}