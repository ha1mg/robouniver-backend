package ru.robouniver.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.robouniver.data.VenueResponse
import ru.robouniver.data.venues.Teachersvenues

fun Route.getVenues(
    venueDataSource: Teachersvenues
) {
    authenticate{
        get("venue") {
            val principal = call.principal<JWTPrincipal>()
            val teacherId = principal?.getClaim("teacherId", Int::class)

            if (teacherId == null) {
                call.respond(HttpStatusCode.Conflict, "erorr")
            } else {
                val venues = venueDataSource.fetchVenue(teacherId)
                if (venues != null) {call.respond(
                    status = HttpStatusCode.OK,
                    message = VenueResponse(
                        statusCode = HttpStatusCode.OK.value,
                        venues = venues
                    )
                )} else {
                    call.respond(HttpStatusCode.Conflict, "erorr")
                }

            }
        }

    }

}