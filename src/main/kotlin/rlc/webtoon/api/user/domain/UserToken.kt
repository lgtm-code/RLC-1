package rlc.webtoon.api.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction
import rlc.webtoon.api.common.domain.BaseEntity

@SQLRestriction("isDeleted = false")
@Table(name = "user_tokens")
@Entity
class UserToken(
        @JoinColumn(name = "userId")
        @ManyToOne
        val user: User,
        val refreshToken: String
) : BaseEntity() {

    var isDeleted: Boolean? = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun delete() {
        this.isDeleted = true
    }
}