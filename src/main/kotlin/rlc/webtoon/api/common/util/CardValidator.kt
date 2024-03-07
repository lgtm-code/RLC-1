package rlc.webtoon.api.common.util

object CardValidator {
    fun validateUserCard(birth: String, cardNumber: String, expiry: String, pw2digit: String) {
        if (!isValidBirth(birth)) {
            throw IllegalArgumentException("형식이 맞지 않은 생년월일 또는 사업자 등록번호입니다. 예) 생년월일:941220 또는 사업자 등록번호: 0124249182 형식으로 입력해주세요.")
        }
        if (!isValidCardNumber(cardNumber)) {
            throw IllegalArgumentException("올바르지 않은 형식의 카드 번호입니다. 예) 1111-2222-3333-4444 또는 02-852-2420 형식으로 입력해주세요.")
        }
        if (!isValidExpiry(expiry)) {
            throw IllegalArgumentException("올바르지 않은 형식의 만료일입니다. 예) 2029-08 형식으로 입력해주세요.")
        }
        if (!isValidPw2Digit(pw2digit)) {
            throw IllegalArgumentException("올바르지 않은 형식의 비밀번호 2자리 입니다. 예) 89 형식으로 입력해주세요.")
        }
    }

    private fun isValidPw2Digit(pw2digit: String): Boolean {
        return pw2digit.matches(Regex("\\d{2}"))
    }

    private fun isValidCardNumber(cardNumber: String): Boolean {
        return cardNumber.matches(Regex("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))
    }

    private fun isValidExpiry(expiry: String): Boolean {
        return expiry.matches(Regex("\\d{4}-\\d{2}"))
    }

    private fun isValidBirth(birth: String): Boolean {
        return birth.matches(Regex("\\d{6}|\\d{10}"))
    }
}