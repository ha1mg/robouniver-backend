package ru.robouniver.data

import kotlinx.serialization.Serializable

@Serializable
data class VenueResponse(
    val statusCode: Int,
    val venue: List<String>
)