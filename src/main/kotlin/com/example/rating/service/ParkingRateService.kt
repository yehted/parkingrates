package com.example.rating.service

import com.example.rating.model.ParkingRate
import com.example.rating.repository.RateRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.ZonedDateTime

@Service
class ParkingRateService(
    private val parkingRateRepository: RateRepository
) {
    fun getAllRates(): List<ParkingRate> {
        return parkingRateRepository.findAll()
    }

    fun getByDayOfWeek(day: DayOfWeek): List<ParkingRate> {
        return parkingRateRepository.findByDaysOfWeekDay(day)
    }

    fun calculateRate(startTime: ZonedDateTime, endTime: ZonedDateTime): List<ParkingRate> {
        if (startTime.year != endTime.year ||
            startTime.dayOfYear != endTime.dayOfYear ||
            startTime.isAfter(endTime)
        ) {
            return listOf()
        }

        val dayOfWeek = startTime.dayOfWeek
        return parkingRateRepository.findByDaysOfWeekDay(dayOfWeek)
            .filter {
                it.startTime.isBefore(startTime.toLocalTime()) && it.endTime.isAfter(endTime.toLocalTime())
            }
    }
}