package com.rlc.webtoon.service

import com.rlc.webtoon.dto.request.PaymentDto
import com.rlc.webtoon.dto.response.PaymentHistoryResponse
import com.rlc.webtoon.entity.User
import com.rlc.webtoon.repostiory.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val paymentService: PaymentService,
) {

    @Test
    @DisplayName("결제 요청이 정상동작한다.")
    fun test(){
        val user = userRepository.save(User.fixture())
        val paymentHistoryResponse: PaymentHistoryResponse = paymentService.payment(
            userUuid = user.uuid,
            paymentDto = PaymentDto.fixture()
        )
        
        Assertions.assertThat(paymentHistoryResponse.productName).isEqualTo("300잎")
        Assertions.assertThat(paymentHistoryResponse.price).isEqualTo(300)
    }
}