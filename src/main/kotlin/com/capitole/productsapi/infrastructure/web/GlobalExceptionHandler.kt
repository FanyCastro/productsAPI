package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.infrastructure.web.dto.ErrorResponseDto
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler () {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationExceptions(
        ex: ConstraintViolationException
    ): ErrorResponseDto {
        logger.error("Invalid parameters ConstraintViolationException - ${ex.constraintViolations}")
        val errors = ex.constraintViolations.map { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }

        return ErrorResponseDto(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Validation failed",
                errors = errors
            )
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericError(ex: RuntimeException): ErrorResponseDto {
        logger.error("Something went wrong - ${ex.javaClass} ${ex.message}")
        return ErrorResponseDto(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal server error",
            errors = listOf("SERVER_ERROR ${ex.message}")
        )
    }
}
