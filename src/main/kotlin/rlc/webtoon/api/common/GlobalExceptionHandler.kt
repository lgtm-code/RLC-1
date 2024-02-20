package rlc.webtoon.api.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [ApiError::class])
    fun handle(e: ApiError) =
            run {
                ResponseEntity(ErrorResponse(e.message), e.error.status)
            }

    @ExceptionHandler(value = [Exception::class])
    fun handle(e: java.lang.Exception) =
            run {
                ResponseEntity(ErrorResponse(e.message), HttpStatus.INTERNAL_SERVER_ERROR)
            }

}