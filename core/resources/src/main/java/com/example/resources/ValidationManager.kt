package com.example.resources

import android.content.Context
import com.example.domain.validators.ValidationErrorCode
import com.example.domain.validators.ValidationException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidationManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun getValidationMessageFrom(error: Exception): String {
       return error.takeIf { it is ValidationException }?.let {
           getErrorMessage((it as ValidationException).code)
       } ?: error.message ?: context.getString(R.string.something_went_wrong)
    }

    private fun getErrorMessage(errorCode: ValidationErrorCode): String {
       return when(errorCode) {
            ValidationErrorCode.EMAIL_NULL_OR_EMPTY -> context.getString(R.string.email_can_not_be_null_or_empty)
            ValidationErrorCode.EMAIL_NOT_VALID -> context.getString(R.string.email_is_not_valid)
            ValidationErrorCode.PASSWORD_NULL_OR_EMPTY -> context.getString(R.string.password_can_not_be_null_or_empty)
            ValidationErrorCode.PASSWORD_LENGTH_LOW -> context.getString(R.string.password_should_be_at_least_of_6_chars)
            ValidationErrorCode.PASSWORD_LENGTH_OVERFLOW -> context.getString(R.string.password_can_not_be_more_that_30_characters)
            ValidationErrorCode.PASSWORD_WEAK -> context.getString(R.string.please_create_password_with_upper_case_lower_case_digit_and_special_characters)
        }
    }
}