package ru.robouniver.plugins

import io.ktor.server.routing.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.robouniver.data.teachers.Teachers
import ru.robouniver.data.venues.Teachersvenues
import ru.robouniver.routes.getSecretInfo
import ru.robouniver.routes.getVenues
import ru.robouniver.routes.signIn
import ru.robouniver.routes.signUp
import ru.robouniver.security.hashing.HashingService
import ru.robouniver.security.token.JwtTokenService
import ru.robouniver.security.token.TokenConfig

fun Application.configureRouting(
    teacherDataSource: Teachers,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig,
    venueDataSource: Teachersvenues
) {

    routing {
        signUp(hashingService = hashingService, teacherDataSource = teacherDataSource)
        signIn(teacherDataSource, hashingService, tokenService, tokenConfig)
        getSecretInfo()
        getVenues(venueDataSource)
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
