package rlc.webtoon.api.user.infra

import org.springframework.data.jpa.repository.JpaRepository
import rlc.webtoon.api.user.domain.UserToken

interface UserTokenRepository : JpaRepository<UserToken, Long> {

}