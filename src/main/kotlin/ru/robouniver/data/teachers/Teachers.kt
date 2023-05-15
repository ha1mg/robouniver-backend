package ru.robouniver.data.teachers

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
object Teachers : Table() {
    private val login = Teachers.varchar("login", 25)
    private val password = Teachers.varchar("password", 64)
    private val name = Teachers.varchar("name", 50)
    private val salt = Teachers.varchar("salt", 64)

    fun insert(teacherDTO: TeacherDTO) {
        transaction {
            Teachers.insert {
                it[login] = teacherDTO.login
                it[password] = teacherDTO.password
                it[name] = teacherDTO.name
                it[salt] = teacherDTO.salt
            }
        }
    }

    fun fetchUser(login: String): TeacherDTO? {
        return try {
            transaction {
                val teacherModel = Teachers.select { Teachers.login.eq(login) }.single()
                TeacherDTO(
                    login = teacherModel[Teachers.login],
                    password = teacherModel[password],
                    name = teacherModel[name],
                    salt = teacherModel[salt]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}