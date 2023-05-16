package ru.robouniver.data.venues

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction



object Venues : Table() {
    val id = Venues.varchar("id", 25)
    val name = Venues.varchar("name", 25)
    val discription = Venues.varchar("discription", 100)
    val address = Venues.varchar("adress", 100)
}


object Teachersvenues: Table() {
    val teacher = Teachersvenues.integer("teacher")
    val venue = Teachersvenues.varchar("venue", length = 25) //references Venues.id


    fun fetchVenue(teacherId: Int): List<VenueDTO>? {
        return try {
            transaction {
                val tvModel = Teachersvenues.select { teacher.eq(teacherId) }
                Venues.select { Venues.id.eq(tvModel.forEach()) }
                    .toList().map{
                        VenueDTO(
                            id = it[Venues.id],
                            name = it[Venues.name],
                            discription = it[Venues.discription],
                            address = it[Venues.address]
                        )
                    }
            }
        } catch (e: Exception) {
             null
        }
    }
}



