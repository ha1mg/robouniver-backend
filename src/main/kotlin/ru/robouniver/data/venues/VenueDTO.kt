package ru.robouniver.data.venues

import kotlinx.serialization.Serializable

@Serializable
data class VenueDTO(
    val id: Int,
    val name: String,
    val discription: String,
    val address: String,

)