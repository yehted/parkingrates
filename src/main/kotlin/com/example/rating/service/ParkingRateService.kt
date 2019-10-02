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
    fun addRate(rate: ParkingRate): ParkingRate {
        val daysOfWeek: List<DayOfWeek> = rate.weekDays?.map { it.day } ?: listOf()

        val doesOverlap: Boolean = daysOfWeek
            .flatMap {
                parkingRateRepository.findByWeekDaysDay(it)
            }
            .any {
                it.isOverlappingWith(rate) == true
            }

        return if (!doesOverlap) {
            parkingRateRepository.save(rate.apply {
                this.weekDays?.forEach {
                    it.parkingRate = this
                }
            })
        } else {
            throw ParkingRateOverlapsWithExistingRate()
        }
    }

    fun getAllRates(): List<ParkingRate> {
        return parkingRateRepository.findAll()
    }

    fun getById(id: Long): ParkingRate? = parkingRateRepository.findById(id).orElse(null)

    fun deleteById(id: Long) = parkingRateRepository.deleteById(id)

    fun getByDayOfWeek(day: DayOfWeek): List<ParkingRate> {
        return parkingRateRepository.findByWeekDaysDay(day)
    }

    fun calculateRate(startTime: ZonedDateTime, endTime: ZonedDateTime): List<ParkingRate> {
        if (startTime.year != endTime.year ||
            startTime.dayOfYear != endTime.dayOfYear ||
            startTime.isAfter(endTime)
        ) {
            return listOf()
        }

        val dayOfWeek = startTime.dayOfWeek
        return parkingRateRepository.findByWeekDaysDay(dayOfWeek)
            .filter {
                it.isWithinRateWindow(startTime.toLocalTime(), endTime.toLocalTime())
            }
    }
}

class ParkingRateNotFoundException(message: String = "Rate not found"): Exception(message)
class ParkingRateOverlapsWithExistingRate(message: String = "Rate overlaps with existing rate"): Exception(message)