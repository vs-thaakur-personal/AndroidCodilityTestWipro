package com.example.domain.usecases

import com.example.data.repositories.AuthRepository
import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.domain.validators.LoginRequestValidator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRequestValidator: LoginRequestValidator,
    private val repository: AuthRepository
) : BaseSuspendedUseCase<LoginRequest, Result<LoginResponse>> {

    override suspend fun execute(input: LoginRequest): Result<LoginResponse> {
        return loginRequestValidator.isValid(input)
            .mapCatching { repository.signIn(input).getOrThrow() }
    }
}