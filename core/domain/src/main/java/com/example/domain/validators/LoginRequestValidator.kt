package com.example.domain.validators

import com.example.data.models.request.LoginRequest
import javax.inject.Inject

class LoginRequestValidator @Inject constructor(
    private val emailValidator: UserNameValidator,
    private val passwordValidator: PasswordValidator
) : BaseValidator<LoginRequest> {
    override fun isValid(data: LoginRequest): Result<Boolean> {
        return emailValidator.isValid(data.userName)
            .mapCatching { passwordValidator.isValid(data.password).getOrThrow() }
    }
}