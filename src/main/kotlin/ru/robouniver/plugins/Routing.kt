package ru.robouniver.plugins

import io.ktor.server.routing.*

import io.ktor.server.application.*
import ru.robouniver.data.groups.Groups
import ru.robouniver.data.teachers.Teachers
import ru.robouniver.data.venues.Teachersvenues
import ru.robouniver.routes.*
import ru.robouniver.security.hashing.HashingService
import ru.robouniver.security.token.JwtTokenService
import ru.robouniver.security.token.TokenConfig

fun Application.configureRouting(
    teacherDataSource: Teachers,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig,
    venueDataSource: Teachersvenues,
//    groupsDataSource: Groups
) {
    routing {
        signUp(hashingService = hashingService, teacherDataSource = teacherDataSource)
        signIn(teacherDataSource, hashingService, tokenService, tokenConfig)
        getSecretInfo()
        getVenues(venueDataSource)
//        getVenue(groupsDataSource)
    }
}
