package com.example.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int,
    @SerialName("username")val userName: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val image: String = "",
    val token: String = "",
)

