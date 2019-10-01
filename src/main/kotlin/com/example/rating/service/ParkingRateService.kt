package com.example.rating.service

import com.example.rating.model.ParkingRate
import com.example.rating.repository.RateRepository
import org.springframework.stereotype.Service

@Service
class ParkingRateService(
    private val parkingRateRepository: RateRepository
) {
    fun getAllRates(): List<ParkingRate> {
        return parkingRateRepository.findAll()
    }
}