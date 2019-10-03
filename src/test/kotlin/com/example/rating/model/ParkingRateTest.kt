package com.example.rating.model

import org.junit.Assert
import org.junit.Test
import java.time.*

class ParkingRateTest {
    private val timeZone = ZoneOffset.of("-05:00")
    private val parkingRate = ParkingRate(
        weekDays = listOf(WeekDay(day = DayOfWeek.MONDAY)),
        startTime = OffsetTime.of(1, 0, 0, 0, timeZone),
        endTime = OffsetTime.of(5, 0, 0, 0, timeZone),
        price = 100
    )

    @Test
    fun `Should assert isWithinRateWindow`() {
        val start = OffsetTime.of(2, 0, 0, 0, timeZone)
        val end = OffsetTime.of(4, 0, 0, 0, timeZone)
        Assert.assertTrue(parkingRate.isWithinRateWindow(start, end))
    }

    @Test
    fun `Should assert isWithinRateWindow on edge`() {
        val start = OffsetTime.of(1, 0, 0, 0, timeZone)
        val end = OffsetTime.of(4, 0, 0, 0, timeZone)
        Assert.assertTrue(parkingRate.isWithinRateWindow(start, end))
    }

    @Test
    fun `Should assert not isWithinRateWindow`() {
        val start = OffsetTime.of(2, 0, 0, 0, timeZone)
        val end = OffsetTime.of(6, 0, 0, 0, timeZone)
        Assert.assertFalse(parkingRate.isWithinRateWindow(start, end))
    }

    @Test
    fun `Should assert isWithinRateWindow in different timezone`() {
        val zone = ZoneOffset.of("+06:00")
        val start = OffsetTime.of(12, 0, 0, 0, zone)
        val end = OffsetTime.of(13, 0, 0, 0, zone)
        Assert.assertTrue(parkingRate.isWithinRateWindow(start, end))
    }

    @Test
    fun `Should assert isOverlappingTimeWith`() {
        val otherRate = parkingRate.copy(
            startTime = OffsetTime.of(4, 0, 0, 0, timeZone),
            endTime = OffsetTime.of(8, 0, 0, 0, timeZone)
        )

        Assert.assertTrue(parkingRate.isOverlappingTimeWith(otherRate))
    }

    @Test
    fun `Should assert not isOverlappingTimeWith`() {
        val otherRate = parkingRate.copy(
            startTime = OffsetTime.of(6, 0, 0, 0, timeZone),
            endTime = OffsetTime.of(8, 0, 0, 0, timeZone)
        )

        Assert.assertFalse(parkingRate.isOverlappingTimeWith(otherRate))
    }

    @Test
    fun `Should assert isOverlappingTimeWith on ending edge`() {
        val otherRate = parkingRate.copy(
            startTime = OffsetTime.of(5, 0, 0, 0, timeZone),
            endTime = OffsetTime.of(8, 0, 0, 0, timeZone)
        )

        Assert.assertTrue(parkingRate.isOverlappingTimeWith(otherRate))
    }

    @Test
    fun `Should assert isOverlappingTimeWith on starting edge`() {
        val otherRate = parkingRate.copy(
            startTime = OffsetTime.of(0, 0, 0, 0, timeZone),
            endTime = OffsetTime.of(1, 0, 0, 0, timeZone)
        )

        Assert.assertTrue(parkingRate.isOverlappingTimeWith(otherRate))
    }
}