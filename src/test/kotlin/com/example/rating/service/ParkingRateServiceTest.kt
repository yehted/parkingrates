package com.example.rating.service

import com.example.rating.model.ParkingRate
import com.example.rating.repository.RateRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.DayOfWeek
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ ParkingRateService::class ])
class ParkingRateServiceTest {
    @MockBean
    lateinit var parkingRepository: RateRepository

    @Autowired
    lateinit var parkingRateService: ParkingRateService

    private val rate1 = ParkingRate(
        startTime = OffsetTime.of(1, 0, 0, 0, ZoneOffset.of("-05:00")),
        endTime = OffsetTime.of(7, 0, 0, 0, ZoneOffset.of("-05:00")),
        price = 1400
    )

    @Test
    fun `Should find appropriate properly`() {
        Mockito.`when`(parkingRepository.findByWeekDaysDay(DayOfWeek.THURSDAY))
            .thenReturn(listOf(rate1))

        val startTime = OffsetDateTime.of(2019, 10, 3, 2, 0, 0, 0, ZoneOffset.of("-05:00"))
        val endTime = OffsetDateTime.of(2019, 10, 3, 4, 0, 0, 0, ZoneOffset.of("-05:00"))

        Assert.assertEquals(rate1, parkingRateService.calculateRate(startTime, endTime))
    }

    @Test
    fun `Should not find appropriate properly`() {
        Mockito.`when`(parkingRepository.findByWeekDaysDay(DayOfWeek.THURSDAY))
            .thenReturn(listOf(rate1))

        val startTime = OffsetDateTime.of(2019, 10, 3, 2, 0, 0, 0, ZoneOffset.of("-05:00"))
        val endTime = OffsetDateTime.of(2019, 10, 3, 8, 0, 0, 0, ZoneOffset.of("-05:00"))

        Assert.assertNull(parkingRateService.calculateRate(startTime, endTime))
    }

    @Test
    fun `Should handle invalid input`() {
        Mockito.`when`(parkingRepository.findByWeekDaysDay(DayOfWeek.THURSDAY))
            .thenReturn(listOf(rate1))

        // not same day
        val startTime = OffsetDateTime.of(2019, 10, 3, 2, 0, 0, 0, ZoneOffset.of("-05:00"))
        val endTime = OffsetDateTime.of(2019, 10, 4, 4, 0, 0, 0, ZoneOffset.of("-05:00"))
        Assert.assertNull(parkingRateService.calculateRate(startTime, endTime))

        // same day of the year, but not same year
        val startTime1 = OffsetDateTime.of(2019, 10, 3, 2, 0, 0, 0, ZoneOffset.of("-05:00"))
        val endTime1 = OffsetDateTime.of(2018, 10, 3, 4, 0, 0, 0, ZoneOffset.of("-05:00"))
        Assert.assertNull(parkingRateService.calculateRate(startTime1, endTime1))

        // start time is not before end time
        val startTime2 = OffsetDateTime.of(2019, 10, 3, 4, 0, 0, 0, ZoneOffset.of("-05:00"))
        val endTime2 = OffsetDateTime.of(2019, 10, 3, 2, 0, 0, 0, ZoneOffset.of("-05:00"))
        Assert.assertNull(parkingRateService.calculateRate(startTime2, endTime2))
    }
}