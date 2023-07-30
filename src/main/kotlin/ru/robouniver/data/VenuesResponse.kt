package ru.robouniver.data


import kotlinx.serialization.*
import ru.robouniver.data.venues.VenueDTO

@Serializable
data class VenuesResponse(
    val statusCode: Int,
    val venues: List<VenueDTO>
)