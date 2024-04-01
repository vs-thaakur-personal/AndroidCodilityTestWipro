package com.example.domain.validators

interface BaseValidator<T> {
    fun isValid(data: T) : Result<Boolean>
}

class ValidationException(val code: ValidationErrorCode): IllegalArgumentException("Validation Failed")

enum class ValidationErrorCode{
    EMAIL_NULL_OR_EMPTY,
    EMAIL_NOT_VALID,
    PASSWORD_NULL_OR_EMPTY,
    PASSWORD_LENGTH_LOW,
    PASSWORD_LENGTH_OVERFLOW,
    PASSWORD_WEAK
}