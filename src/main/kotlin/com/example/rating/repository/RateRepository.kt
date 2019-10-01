package com.example.rating.repository

import com.example.rating.model.ParkingRate
import org.springframework.data.jpa.repository.JpaRepository

interface RateRepository : JpaRepository<ParkingRate, Long> {

}

class InvalidDayOfWeekException(message: String = "Unrecognized day of week"): Exception(message)