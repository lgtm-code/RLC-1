package com.rlc.webtoon.repostiory

import com.rlc.webtoon.entity.WebtoonDetail
import org.springframework.data.jpa.repository.JpaRepository

interface WebtoonDetailRepository: JpaRepository<WebtoonDetail, String> {
}