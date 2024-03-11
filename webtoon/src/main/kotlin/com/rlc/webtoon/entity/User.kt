package com.rlc.webtoon.entity

import com.rlc.webtoon.entity.common.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

//TODO 각 필드 length, unique 설정등..
@Entity
@Table(name = "tb_user")
class User constructor(
    val loginId: String,
    var password: String,
    val email: String,
    val phoneNumber: String,
    val birth: String,

    @Id
    val uuid: String = UUID.randomUUID().toString(),

    var refreshToken: String? = null
): BaseTimeEntity() {
    var point = 0

    override fun toString(): String {
        return "User(loginId='$loginId', email='$email', phoneNumber='$phoneNumber', birth='$birth', uuid=$uuid, refreshToken=$refreshToken)"
    }

    companion object {
        fun fixture(): User {
            return User(
                loginId = "test@test.com",
                email = "test@test.com",
                password = "1234",
                phoneNumber = "01012345678",
                birth = "20000101",
            )
        }
    }
}