package com.example.rating.controller

import com.example.rating.model.ParkingRate
import com.example.rating.service.ParkingRateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rates")
class ParkingRateController(
    private val parkingRateService: ParkingRateService
) {
    @GetMapping
    fun getAllRates(): ResponseEntity<List<ParkingRate>> {
        return ResponseEntity(
            parkingRateService.getAllRates(),
            HttpStatus.OK
        )
    }
}