package ru.robouniver.data.teachers

data class TeacherDTO(
    val id: Int,
    val login: String,
    val password: String,
    val name: String,
    val salt: String
)
