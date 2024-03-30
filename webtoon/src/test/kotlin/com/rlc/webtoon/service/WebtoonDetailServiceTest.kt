package com.rlc.webtoon.service

import com.rlc.webtoon.dto.enumerated.PaymentType
import com.rlc.webtoon.dto.request.WebtoonDetailRequestDto
import com.rlc.webtoon.dto.request.WebtoonDto
import com.rlc.webtoon.dto.response.WebtoonEpisodeResponse
import com.rlc.webtoon.dto.response.WebtoonPaymentResponse
import com.rlc.webtoon.entity.User
import com.rlc.webtoon.entity.Webtoon
import com.rlc.webtoon.entity.WebtoonDetail
import com.rlc.webtoon.exception.RlcClientException
import com.rlc.webtoon.repostiory.UserRepository
import com.rlc.webtoon.repostiory.WebtoonDetailRepository
import com.rlc.webtoon.repostiory.WebtoonRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class WebtoonDetailServiceTest @Autowired constructor(
    val webtoonDetailService: WebtoonDetailService,
    val webtoonRepository: WebtoonRepository,
    val webtoonDetailRepository: WebtoonDetailRepository,
    val userRepository: UserRepository
) {

    @BeforeAll
    fun saveDetailWebtoon() {
        val webtoon: Webtoon = webtoonRepository.save(
            WebtoonDto.fixture(
                title = "마음의 소리",
                dayOfWeek = DayOfWeek.TUESDAY
            ).toEntity()
        )

        val firstWebtoon: WebtoonDetailRequestDto =
            WebtoonDetailRequestDto.fixture(episode = "1화", displayDate = Instant.MIN)

        val lastWebtoon: WebtoonDetailRequestDto =
            WebtoonDetailRequestDto.fixture(episode = "마지막화", displayDate = Instant.MAX)

        webtoonDetailService.saveWebtoonDetail(webtoon.id!!, firstWebtoon)
        webtoonDetailService.saveWebtoonDetail(webtoon.id!!, lastWebtoon)
    }

    @Test
    @DisplayName("웹툰 정상 등록 확인.")
    fun saveWebtoonDetail() {
        val webtoonList = webtoonDetailRepository.findAll()
        assertThat(webtoonList).hasSize(2)
        assertThat(webtoonList).extracting("episode").containsExactlyInAnyOrder("1화", "마지막화")
    }

    @Test
    @DisplayName("공개된 웹툰에 대하여 내용이 정상 조회된다.")
    fun viewDetailWebtoon() {
        //given
        val saveUser = userRepository.save(User.fixture())
        val webtoonList = webtoonDetailRepository.findAll()

        //when
        val webtoonEpisode: WebtoonEpisodeResponse = webtoonDetailService.viewWebtoonDetail(webtoonList[0].uuid, saveUser.uuid)

        //then
        assertThat(webtoonEpisode.episode).isEqualTo("1화")
    }

    @Test
    @DisplayName("미공개된 웹툰을 결제하지 않은 유저는 웹툰 조회에 실패한다.")
    fun failedToViewDetailWebtoon() {
        //given
        val saveUser = userRepository.save(User.fixture())
        val webtoonList = webtoonDetailRepository.findAll()

        //when
        val errorMessage = assertThrows<RlcClientException> {
            webtoonDetailService.viewWebtoonDetail(webtoonList[1].uuid, saveUser.uuid)
        }.errorMessage

        //then
        assertThat(errorMessage).isEqualTo("결제가 필요한 웹툰입니다.")
    }

    @Test
    @DisplayName("미공개 된 웹툰을 결제한 이후 정상 조회된다.")
    fun viewPaidWebtoon() {
        //given
        val saveUser = userRepository.save(
            User.fixture().apply {
                this.point += 500
            }
        )
        val webtoonList = webtoonDetailRepository.findAll()
        val lockedWebtoon: WebtoonDetail = webtoonList[1]

        //when
        webtoonDetailService.payWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)
        val viewWebtoonDetail = webtoonDetailService.viewWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)
        
        //then
        assertThat(viewWebtoonDetail.episode).isEqualTo("마지막화")
    }

    @Test
    @DisplayName("미공개된 웹툰에 대하여 결제가 정상 동작한다.")
    fun paidWebtoon() {
        //given
        val saveUser = userRepository.save(
            User.fixture().apply {
                this.point += 500
            }
        )
        val webtoonList = webtoonDetailRepository.findAll()
        val lockedWebtoon: WebtoonDetail = webtoonList[1]

        //when
        val webtoonPaymentResponse: WebtoonPaymentResponse =
            webtoonDetailService.payWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)

        //then
        assertThat(saveUser.point).isEqualTo(0)
        assertThat(webtoonPaymentResponse.webtoonEpisode).isEqualTo("마지막화")
    }

    @Test
    @DisplayName("포인트가 부족한 유저는 결제가 실패한다.")
    fun failedToPayLackPointUser() {
        //given
        val saveUser = userRepository.save(
            User.fixture().apply {
                this.point += 300
            }
        )
        val webtoonList = webtoonDetailRepository.findAll()
        val lockedWebtoon: WebtoonDetail = webtoonList[1]

        //when
        val errorMessage = assertThrows<RlcClientException> {
            webtoonDetailService.payWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)
        }.errorMessage

        //then
        assertThat(errorMessage).isEqualTo("결제에 필요한 포인트가 부족합니다. 보유 포인트: 300")
    }

    @Test
    @DisplayName("결제한 웹툰에 대하여 정상 환불 처리된다.")
    fun refundPaidWebtoon() {
        //given
        val saveUser = userRepository.save(
            User.fixture().apply {
                this.point += 500
            }
        )
        val webtoonList = webtoonDetailRepository.findAll()
        val lockedWebtoon: WebtoonDetail = webtoonList[1]
        val webtoonPaymentResponse: WebtoonPaymentResponse =
            webtoonDetailService.payWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)

        //when
        val refundPaymentInfo = webtoonDetailService.refundPaidWebtoon(saveUser.uuid, webtoonPaymentResponse.id)

        //then
        assertThat(refundPaymentInfo.paymentType).isEqualTo(PaymentType.CANCEL)
        assertThat(saveUser.point).isEqualTo(300)
    }

    @Test
    @DisplayName("결제한 웹툰에 대하여 조회 후 환불처리는 실패한다.")
    fun failRefundPaidWebtoon() {
        //given
        val saveUser = userRepository.save(
            User.fixture().apply {
                this.point += 500
            }
        )
        val webtoonList = webtoonDetailRepository.findAll()
        val lockedWebtoon: WebtoonDetail = webtoonList[1]
        val webtoonPaymentResponse: WebtoonPaymentResponse =
            webtoonDetailService.payWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)

        //when
        webtoonDetailService.viewWebtoonDetail(lockedWebtoon.uuid, saveUser.uuid)
        val errorMessage = assertThrows<RlcClientException> {
            webtoonDetailService.refundPaidWebtoon(saveUser.uuid, webtoonPaymentResponse.id)
        }.errorMessage

        //then
        assertThat(errorMessage).isEqualTo("이미 조회한 웹툰은 환불이 불가능합니다.")
    }
}