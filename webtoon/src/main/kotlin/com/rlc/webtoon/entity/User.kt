package com.rlc.webtoon.entity

import com.rlc.webtoon.dto.request.UserRequestDto
import com.rlc.webtoon.entity.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

//TODO 각 필드 length, unique 설정등..
@Entity
@Table(name = "tb_user")
class User constructor(
    @Column(nullable = false)
    val loginId: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val phoneNumber: String,
    @Column(nullable = false)
    val birth: String,

    @Id
    var uuid: String? = null,

    var refreshToken: String? = null
): BaseTimeEntity() {

    companion object {
        fun of(userRequestDto: UserRequestDto): User {
            return User(
                loginId = userRequestDto.id,
                password = userRequestDto.password,
                email = userRequestDto.email,
                phoneNumber = userRequestDto.phoneNumber,
                birth = userRequestDto.birth,
                uuid = UUID.randomUUID().toString()
            )
        }
    }

    override fun toString(): String {
        return "User(loginId='$loginId', email='$email', phoneNumber='$phoneNumber', birth='$birth', uuid=$uuid, refreshToken=$refreshToken)"
    }

}