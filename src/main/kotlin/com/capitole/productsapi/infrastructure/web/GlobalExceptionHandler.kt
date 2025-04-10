package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.infrastructure.web.dto.ErrorResponse
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class GlobalExceptionHandler () {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationExceptions(
        ex: ConstraintViolationException
    ): ErrorResponse {
        logger.error("Invalid parameters ConstraintViolationException - ${ex.constraintViolations}")
        val errors = ex.constraintViolations.map { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }

        return ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Validation failed",
                errors = errors
            )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericError(ex: Exception): ErrorResponse {
        logger.error("Something went wrong - ${ex.javaClass} ${ex.message}")
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal server error",
            errors = listOf("SERVER_ERROR ${ex.message}")
        )
    }
}
