package ru.robouniver.data


import kotlinx.serialization.SerialName
import ru.robouniver.data.venues.VenueDTO

data class VenueResponse(
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("venues")
    val venues: List<VenueDTO>
)