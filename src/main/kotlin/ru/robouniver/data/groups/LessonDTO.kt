package ru.robouniver.data.groups

data class LessonDTO(
    val id: String,
    val group: String,
    val number: Int,
    val student: Int,
    val visit: Boolean,
    val score: Int
)
