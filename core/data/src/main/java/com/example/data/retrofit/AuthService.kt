package com.example.data.retrofit

import com.example.data.models.request.LoginRequest
import com.example.data.models.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}