package rlc.webtoon.api.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import rlc.webtoon.api.common.BaseEntity

@Table(name = "user_tokens")
@Entity
class UserToken(
        @JoinColumn(name = "userId")
        val user: User,
        val refreshToken: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}