package com.example.domain.validators

import java.util.regex.Pattern
import javax.inject.Inject

class UserNameValidator @Inject constructor() : BaseValidator<String?> {

    override fun isValid(data: String?): Result<Boolean> {
        if(data.isNullOrEmpty()) return Result.failure(ValidationException(ValidationErrorCode.EMAIL_NULL_OR_EMPTY))
        return if(data.length>3) {
            Result.success(true)
        } else {
            Result.failure(ValidationException(ValidationErrorCode.EMAIL_NOT_VALID))
        }
    }
}