package com.rlc.webtoon.repostiory

import com.rlc.webtoon.entity.Webtoon
import org.springframework.data.jpa.repository.JpaRepository
import java.time.DayOfWeek

interface WebtoonRepository: JpaRepository<Webtoon, Long> {

    fun findByDayOfWeek(dayOfWeek: DayOfWeek): List<Webtoon>
}