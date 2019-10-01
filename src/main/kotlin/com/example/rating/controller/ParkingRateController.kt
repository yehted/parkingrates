package com.example.rating.controller

import com.example.rating.model.ParkingRate
import com.example.rating.service.ParkingRateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.ZonedDateTime

@RestController
@RequestMapping("/rates")
class ParkingRateController(
    private val parkingRateService: ParkingRateService
) {
    @GetMapping
    fun getAllRates(@RequestParam startTime: String?, @RequestParam endTime: String?): ResponseEntity<List<ParkingRate>> {
        val rates: List<ParkingRate> = if (startTime != null && endTime != null) {
            val start = ZonedDateTime.parse(startTime)
            val end = ZonedDateTime.parse(endTime)
            parkingRateService.calculateRate(start, end)
        } else {
            parkingRateService.getAllRates()
        }

        return ResponseEntity(
            rates,
            HttpStatus.OK
        )
    }

    @GetMapping("/{day}")
    fun getByDayOfWeek(@PathVariable day: DayOfWeek): ResponseEntity<List<ParkingRate>> {
        return ResponseEntity(
            parkingRateService.getByDayOfWeek(day),
            HttpStatus.OK
        )
    }
}