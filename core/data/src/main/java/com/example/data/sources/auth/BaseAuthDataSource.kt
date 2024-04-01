package com.example.data.sources.auth

import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse

interface BaseAuthDataSource {
    suspend fun signIn(request: LoginRequest) : Result<LoginResponse>
}