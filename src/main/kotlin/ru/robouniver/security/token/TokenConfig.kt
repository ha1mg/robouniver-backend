package ru.robouniver.security.token

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val exiresIn: Long,
    val secret: String
)