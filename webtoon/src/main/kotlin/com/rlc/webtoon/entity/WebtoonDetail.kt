package com.rlc.webtoon.entity

import com.rlc.webtoon.dto.response.WebtoonEpisodeResponse
import com.rlc.webtoon.entity.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "tb_webtoon_detail")
class WebtoonDetail(
    val episode: String, //에피소드명 ex)1화, 2화
    @Lob
    val content: String, //웹툰 파일이라면 어떤 타입이 맞을까?
    @Column
    val displayDate: Instant, //웹툰 공개일자.

    @Enumerated(EnumType.STRING)
    var openType: OpenType = OpenType.OPEN,
    var fee: Int = 0,

    @Id
    val uuid: String = UUID.randomUUID().toString(),
): BaseTimeEntity() {
    @ManyToOne
    lateinit var webtoon: Webtoon

    init {
        if(displayDate >= Instant.now()) {
            openType = OpenType.LOCK
            fee = 500
        }
    }

    fun toDetailResponseDto(): WebtoonEpisodeResponse {
        return WebtoonEpisodeResponse(episode, content)
    }
}

enum class OpenType(){
    OPEN, LOCK
}