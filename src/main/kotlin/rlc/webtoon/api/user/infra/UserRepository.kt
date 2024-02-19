package rlc.webtoon.api.user.infra

import org.springframework.data.jpa.repository.JpaRepository
import rlc.webtoon.api.user.domain.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByAccountId(accountId: String): User?

}