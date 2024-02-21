package rlc.webtoon.api.common

import io.jsonwebtoken.MalformedJwtException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [ApiError::class])
    fun handle(e: ApiError) =
            run {
                ResponseEntity(ErrorResponse(e.message), e.error.status)
            }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handle(e: IllegalArgumentException) =
            run {
                ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)
            }

    @ExceptionHandler(value = [EmptyResultDataAccessException::class, NoSuchElementException::class])
    fun handle(e: Exception) =
            run {
                ResponseEntity(ErrorResponse("존재하지 않는 데이터입니다."), HttpStatus.NOT_FOUND)
            }

    @ExceptionHandler(value = [MalformedJwtException::class])
    fun handle(e: MalformedJwtException) =
            run {
                ResponseEntity(ErrorResponse("유효하지 않은 JWT 입니다."), HttpStatus.UNAUTHORIZED)
            }


}