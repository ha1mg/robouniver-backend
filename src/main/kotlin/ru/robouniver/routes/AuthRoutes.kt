package ru.robouniver.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

import ru.robouniver.data.teachers.Teachers
import ru.robouniver.data.teachers.TeacherDTO
import ru.robouniver.data.AuthResponse
import ru.robouniver.data.SignInRequest
import ru.robouniver.data.SignUpRequest


import ru.robouniver.security.hashing.HashingService
import ru.robouniver.security.hashing.SaltedHash
import ru.robouniver.security.token.JwtTokenService
import ru.robouniver.security.token.TokenClaim
import ru.robouniver.security.token.TokenConfig

fun Route.signUp(
    hashingService: HashingService,
    teacherDataSource: Teachers
) {
    post("signup") {
        val request = kotlin.runCatching { call.receiveNullable<SignUpRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val areFieldsBlank = request.login.isBlank() || request.password.isBlank()
        val isPasswordShort = request.password.length < 8

        if (areFieldsBlank || isPasswordShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        try {
            teacherDataSource.insert(
                TeacherDTO(
                    login = request.login,
                    password = saltedHash.hash,
                    name = request.name,
                    salt = saltedHash.salt
                )
            )
            call.respond(HttpStatusCode.OK)
        } catch (e: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
        }
    }
}


fun Route.signIn(
    teacherDataSource: Teachers,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = kotlin.runCatching { call.receiveNullable<SignInRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val teacher = teacherDataSource.fetchUser(request.login)
        if (teacher == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect login or password")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = teacher.password,
                salt = teacher.salt
            )
        )

        if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "teacherLogin",
                value = teacher.login
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token
            )
        )
    }
}


fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("teacherLogin", String::class)
            call.respond(HttpStatusCode.OK, "Your login: $userId")
        }
    }
}