package com.example.domain.validators

import javax.inject.Inject

class PasswordValidator @Inject constructor() : BaseValidator<String?> {

    override fun isValid(data: String?): Result<Boolean> {
        if (data.isNullOrEmpty()) return Result.failure(ValidationException(ValidationErrorCode.PASSWORD_NULL_OR_EMPTY))
//        if(data.length<6) return Result.failure(ValidationException(ValidationErrorCode.PASSWORD_LENGTH_LOW))
//        if(data.length>30) return Result.failure(ValidationException(ValidationErrorCode.PASSWORD_LENGTH_OVERFLOW))
//        if(isWeak(data)) return Result.failure(ValidationException(ValidationErrorCode.PASSWORD_WEAK))
        return Result.success(true)
    }

    private fun isWeak(password: String): Boolean {
        val hasUppercase = password.any { char -> char.isUpperCase() }
        val hasLowercase = password.any { char -> char.isLowerCase() }
        val hasSpecial = password.any { char -> char in "!@#$%^&*()-_=+[{\\]};':\",<.>/?|\\" }
        val hasDigit = password.any { char -> char.isDigit() }
        return !(hasUppercase && hasLowercase && hasSpecial && hasDigit)
    }
}