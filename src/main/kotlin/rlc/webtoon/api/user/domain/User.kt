package rlc.webtoon.api.user.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import rlc.webtoon.api.common.BaseEntity
import java.lang.IllegalArgumentException

@Table(name = "users")
@Entity
class User(
        @Column(unique = true)
        val accountId: String,
        val password: String
) : BaseEntity() {
    init {
        require(accountId.isNotBlank()) {
            "accountId 필수 값 입니다."
        }
        require(password.isNotBlank()) {
            "password 필수 값 입니다."
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tokens: MutableList<UserToken> = mutableListOf()

    fun addToken(refreshToken: String) {
        val token = UserToken(
                user = this,
                refreshToken = refreshToken
        )

        this.tokens.add(token)
    }
}