package com.example.rating.model

import org.junit.Assert
import org.junit.Test
import java.time.*

class ParkingRateDaoTest {

    @Test
    fun `Should convert toEntity`() {
        val dao = ParkingRateDao(
            days = "mon,tues,wed",
            times = "0100-0600",
            tz = "America/Chicago",
            price = 100
        )

        val expected = ParkingRate(
            weekDays = listOf(WeekDay(day = DayOfWeek.MONDAY), WeekDay(day = DayOfWeek.TUESDAY), WeekDay(day = DayOfWeek.WEDNESDAY)),
            startTime = OffsetTime.of(1, 0, 0, 0, ZoneOffset.of("-05:00")),
            endTime = OffsetTime.of(6, 0, 0, 0, ZoneOffset.of("-05:00")),
            price = 100
        )

        Assert.assertEquals(expected, dao.toEntity())
    }
}