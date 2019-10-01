package com.example.rating.service

import com.example.rating.model.ParkingRate
import com.example.rating.repository.RateRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
class ParkingRateService(
    private val parkingRateRepository: RateRepository
) {
    fun getAllRates(): List<ParkingRate> {
        return parkingRateRepository.findAll()
    }

//    fun getByDayOfWeek(day: DayOfWeek): List<ParkingRate> {
//        return parkingRateRepository.
//    }
}