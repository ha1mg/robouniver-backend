package ru.robouniver.data

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val login: String,
    val password: String,
    val name: String
)
