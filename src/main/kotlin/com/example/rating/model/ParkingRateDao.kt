package com.example.rating.model

import com.example.rating.repository.InvalidDayOfWeekException
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId

data class ParkingRateDao(
    val days: String,
    val times: String,
    val tz: String,
    val price: Int
) {
    companion object {
        private val DAY_OF_WEEK_MAP = mapOf(
            "sun" to DayOfWeek.SUNDAY,
            "mon" to DayOfWeek.MONDAY,
            "tues" to DayOfWeek.TUESDAY,
            "wed" to DayOfWeek.WEDNESDAY,
            "thurs" to DayOfWeek.THURSDAY,
            "fri" to DayOfWeek.FRIDAY,
            "sat" to DayOfWeek.SATURDAY
        )
    }

    fun toEntity(): ParkingRate {
        val times: List<String> = times.split("-")

        return ParkingRate(
            daysOfWeek = days.split(",").map {
                com.example.rating.model.DayOfWeek(
                    day = DAY_OF_WEEK_MAP[it] ?: throw InvalidDayOfWeekException()
                )
            },
            startTime = LocalTime.of(times.first().slice(0..1).toInt(), times.first().slice(2..3).toInt(), 0),
            endTime = LocalTime.of(times.last().slice(0..1).toInt(), times.last().slice(2..3).toInt(), 0),
            timeZone = ZoneId.of(tz),
            price = price
        )
    }

}