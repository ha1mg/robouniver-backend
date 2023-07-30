package ru.robouniver.data

import kotlinx.serialization.Serializable

@Serializable
data class LevelResponse(
    val statusCode: Int,
    val level: List<String>
)
