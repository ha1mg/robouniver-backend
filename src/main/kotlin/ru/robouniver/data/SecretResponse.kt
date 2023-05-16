package ru.robouniver.data

import kotlinx.serialization.Serializable

@Serializable
data class SecretResponse(
    val statusCode: Int,
    val id: Int
)