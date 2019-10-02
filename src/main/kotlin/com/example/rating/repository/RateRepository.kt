package com.example.rating.repository

import com.example.rating.model.ParkingRate
import org.springframework.data.jpa.repository.JpaRepository
import java.time.DayOfWeek

interface RateRepository : JpaRepository<ParkingRate, Long> {
    fun findByWeekDaysDay(dayOfWeek: DayOfWeek): List<ParkingRate>
}

class InvalidDayOfWeekException(message: String = "Unrecognized day of week"): Exception(message)