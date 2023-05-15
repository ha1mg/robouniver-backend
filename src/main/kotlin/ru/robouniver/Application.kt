package ru.robouniver

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.robouniver.data.teachers.Teachers
import ru.robouniver.plugins.*
import ru.robouniver.security.hashing.SHA256HashingService
import ru.robouniver.security.token.JwtTokenService
import ru.robouniver.security.token.TokenConfig


fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/robouniver", driver = "org.postgresql.Driver",
        user = "postgres", password = "27Va09dim84!")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val teacherDataSource = Teachers
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = System.getenv("ISSUER"),
        audience = System.getenv("AUDIENCE"),
        exiresIn = 365L * 1000L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(
        teacherDataSource = teacherDataSource,
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}
