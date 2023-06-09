package ru.robouniver.data

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val statusCode: Int,
    val token: String,
    val name: String
)