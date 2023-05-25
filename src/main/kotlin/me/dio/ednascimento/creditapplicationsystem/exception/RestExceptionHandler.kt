package me.dio.ednascimento.creditapplicationsystem.exception

import org.springframework.dao.DataAccessException
import org.springframework.http.*
import org.springframework.validation.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.time.LocalDate

@RestControllerAdvice
class  RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.stream().forEach {
            erro: ObjectError ->
                val fieldName: String = (erro as FieldError).field
                val messageError: String? = erro.defaultMessage
                errors[fieldName] = messageError
        }
        val exceptionDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation",
            timestamp = LocalDate.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.simpleName,
            details = errors)
        return ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handlerValidException(ex: DataAccessException): ResponseEntity<ExceptionDetails> {
        val exceptionDetails = ExceptionDetails(
            title = "Conflict! Consult the documentation",
            timestamp = LocalDate.now(),
            status = HttpStatus.CONFLICT.value(),
            exception = ex.javaClass.simpleName,
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )
        return ResponseEntity(exceptionDetails, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BusinessException::class)
    fun handlerValidException(ex: BusinessException): ResponseEntity<ExceptionDetails> {
        val exceptionDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation",
            timestamp = LocalDate.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.simpleName,
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )
        return ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerValidException(ex: IllegalArgumentException): ResponseEntity<ExceptionDetails> {
        val exceptionDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation",
            timestamp = LocalDate.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.simpleName,
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )
        return ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST)
    }
}