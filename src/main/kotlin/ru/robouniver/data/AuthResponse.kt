package ru.robouniver.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)