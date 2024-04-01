package com.example.data.repositories

import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import com.example.data.sources.auth.BaseAuthDataSource
import com.example.data.toApiException
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authRemoteDataSource: BaseAuthDataSource) {
    suspend fun signIn(request: LoginRequest): Result<LoginResponse> {
        return authRemoteDataSource.signIn(request)
            .recoverCatching {
                throw it.toApiException()
            }
    }
}