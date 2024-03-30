package com.rlc.webtoon.repostiory

import com.rlc.webtoon.entity.WebtoonPaymentHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface WebtoonPaymentHistoryRepository: JpaRepository<WebtoonPaymentHistory, Long> {

    fun findByUserUuidAndWebtoonDetailUuidAndViewPossibleDateGreaterThan(userUuid: String, webtoonDetailUuid: String, time: Instant): WebtoonPaymentHistory?

    fun findByIdAndUserUuid(id: Long, userUuid: String): WebtoonPaymentHistory?
}