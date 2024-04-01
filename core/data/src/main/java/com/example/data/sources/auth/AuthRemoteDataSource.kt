package com.example.data.sources.auth

import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.data.retrofit.AuthService
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val apiService: AuthService
) : BaseAuthDataSource {
    override suspend fun signIn(request: LoginRequest): Result<LoginResponse> {
        return runCatching { apiService.login(request) }
    }
}