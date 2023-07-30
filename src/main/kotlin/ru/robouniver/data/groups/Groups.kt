package ru.robouniver.data.groups

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.robouniver.data.venues.Venues

object Lessons : Table() {
    val id = Lessons.varchar("id", 25)
    val group = Lessons.varchar("group", 25)
    val number = Lessons.integer("number")
    val student = Lessons.integer("student")
    val visit = Lessons.bool("visit")
    val score = Lessons.integer("score")
}

object Groups : Table() {
    val id = Groups.varchar("id", 25)
    val venue = Groups.varchar("venue", 25)
    val level = Groups.varchar("level", 5)
    val teacher= Groups.integer("teacher")

    fun fetchVenuesFromGroups(teacherId: Int) : List<String>? {
        return try {
            transaction {
                Groups.slice(teacher, venue).select { teacher.eq(teacherId) }.toList().map{it[venue]}
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchLevelsByVenue(teacherId: Int, venueName: String) : List<String> {
            return transaction {
                Groups.select { teacher.eq(teacherId) and venue.eq(venueName) }.toList().map{it[level]}
            }
    }

    fun fetchLessonByLevel(teacherId: Int, venueName: String, lvl: String): String {
           return transaction {
                Groups.select { teacher.eq(teacherId) and venue.eq(venueName) and level.eq(lvl)}.toString()
            }
    }

    fun fetchLessons(groupId: String, number: Int) : List<LessonDTO>? {
        return try {
            transaction {
                Lessons.select {Lessons.group.eq(groupId) and Lessons.number.eq(number)}.toList().map {
                    LessonDTO(
                        id = it[Lessons.id],
                        group = it[Lessons.group],
                        number = it[Lessons.number],
                        student = it[Lessons.student],
                        visit = it[Lessons.visit],
                        score = it[Lessons.score]
                    )
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}