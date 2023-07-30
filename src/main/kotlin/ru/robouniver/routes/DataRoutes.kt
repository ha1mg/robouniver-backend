package ru.robouniver.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.robouniver.data.LevelResponse
import ru.robouniver.data.VenueResponse
import ru.robouniver.data.VenuesResponse
import ru.robouniver.data.groups.Groups
import ru.robouniver.data.venues.Teachersvenues

fun Route.getVenues(
    venueDataSource: Teachersvenues
) {
    authenticate{
        get("venues") {
            val principal = call.principal<JWTPrincipal>()
            val teacherId = principal?.getClaim("teacherId", Int::class)
            if (teacherId == null) { call.respond(HttpStatusCode.Conflict, "error") }
            else {
                val venues = venueDataSource.fetchVenue(teacherId)
                println(venues.toString())
                if (venues != null) {call.respond(status = HttpStatusCode.OK,
                    message = VenuesResponse(statusCode = HttpStatusCode.OK.value, venues = venues)
                )} else {
                    call.respond(HttpStatusCode.Conflict, "error")
                }

            }
        }
    }
}
fun Route.getVenue(
    groupsDataSource: Groups
) {
    authenticate{
        get("venue") {
            val principal = call.principal<JWTPrincipal>()
            val teacherId = principal?.getClaim("teacherId", Int::class)
            if (teacherId == null) { call.respond(HttpStatusCode.Conflict, "error") }
            else {
                val venue = groupsDataSource.fetchVenuesFromGroups(teacherId)
                if (venue != null) {call.respond(status = HttpStatusCode.OK,
                    message = VenueResponse(statusCode = HttpStatusCode.OK.value, venue = venue)
                )} else {
                    call.respond(HttpStatusCode.Conflict, "error")
                }

            }
        }
    }
}

fun Route.getLesson(
    venueDataSource: Groups
) {
    authenticate{
        get("level") {
            val principal = call.principal<JWTPrincipal>()
            val teacherId = principal?.getClaim("teacherId", Int::class)
            if (teacherId == null) { call.respond(HttpStatusCode.Conflict, "error") }
            else {
                val level = venueDataSource.fetchVenuesFromGroups(teacherId)
                if (level != null) {call.respond(status = HttpStatusCode.OK,
                    message = LevelResponse(statusCode = HttpStatusCode.OK.value, level = level)
                )} else {
                    call.respond(HttpStatusCode.Conflict, "error")
                }

            }
        }
    }
}

