package com.rlc.webtoon.repostiory

import com.rlc.webtoon.entity.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository: JpaRepository<PaymentHistory, Long> {
}