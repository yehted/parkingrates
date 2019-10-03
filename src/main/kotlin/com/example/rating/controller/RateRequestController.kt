package com.example.rating.controller

import com.example.rating.model.ParkingRate
import com.example.rating.model.RateRequest
import com.example.rating.service.ParkingRateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/requests")
class RateRequestController(
    private val parkingRateService: ParkingRateService
) {
    @PostMapping
    fun getRate(@RequestBody request: RateRequest): ResponseEntity<ParkingRate> {
        val rate = parkingRateService.calculateRate(request.startDate, request.endDate)

        return ResponseEntity.of(Optional.ofNullable(rate))
    }
}