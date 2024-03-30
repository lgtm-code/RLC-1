package com.rlc.webtoon.entity

import com.rlc.webtoon.entity.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.DayOfWeek

@Entity
@Table(name = "tb_webtoon")
class Webtoon(
    val title: String,
    val author: String,
    @Enumerated(EnumType.STRING)
    val dayOfWeek: DayOfWeek, //어떤 요일 웹툰인지 설정

    @OneToMany(mappedBy = "webtoon", cascade = [CascadeType.ALL], orphanRemoval = true)
    val webtoonList: MutableList<WebtoonDetail> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
): BaseTimeEntity() {
}