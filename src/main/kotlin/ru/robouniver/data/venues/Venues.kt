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
    private val teacher = Teachersvenues.integer("teacher")
    private val venue = Teachersvenues.varchar("venue", length = 25) //references Venues.id


    fun fetchVenue(teacherId: Int): List<VenueDTO>? {
        return try {
            transaction {
                val query = Teachersvenues.select { teacher.eq(teacherId) }.toList().map {it[venue]}
                Venues.select { Venues.id inList query  }
                    .toList().map{
                        VenueDTO(
                            id = it[Venues.id],
                            name = it[Venues.name],
                            discription = it[Venues.discription],
                            address = it[Venues.address]
                        )
                    }  }
        } catch (e: Exception) {
             null
        }
    }
}



