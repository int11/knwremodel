package injea.knwremodel

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class globalExceptHandler {

    @ExceptionHandler(value = [NullPointerException::class])
    fun asdf(e: Exception): ResponseEntity<*>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }
}