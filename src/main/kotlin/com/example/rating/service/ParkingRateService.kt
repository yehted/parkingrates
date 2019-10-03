package com.example.rating.service

import com.example.rating.model.ParkingRate
import com.example.rating.repository.RateRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.DayOfWeek
import java.time.OffsetDateTime

@Service
class ParkingRateService(
    private val parkingRateRepository: RateRepository
) {
    fun addRate(rate: ParkingRate): ParkingRate {
        if (!rate.isValid()) throw InvalidParkingRateException()

        val daysOfWeek: List<DayOfWeek> = rate.weekDays?.map { it.day } ?: listOf()

        val doesOverlap: Boolean = daysOfWeek
            .flatMap {
                parkingRateRepository.findByWeekDaysDay(it)
            }
            .any {
                it.isOverlappingTimeWith(rate)
            }

        return if (!doesOverlap) {
            parkingRateRepository.save(rate.apply {
                this.weekDays?.forEach {
                    it.parkingRate = this
                }
            })
        } else {
            throw ParkingRateOverlapsWithExistingRateException()
        }
    }

    fun getAllRates(): List<ParkingRate> = parkingRateRepository.findAll()

    fun getById(id: Long): ParkingRate? = parkingRateRepository.findById(id).orElse(null)

    fun deleteById(id: Long) = parkingRateRepository.deleteById(id)

    fun getByDayOfWeek(day: DayOfWeek): List<ParkingRate> = parkingRateRepository.findByWeekDaysDay(day)

    fun calculateRate(startTime: OffsetDateTime, endTime: OffsetDateTime): ParkingRate? {
        if (startTime.year != endTime.year ||
            startTime.dayOfYear != endTime.dayOfYear ||
            startTime.isAfter(endTime)
        ) {
            return null
        }

        val dayOfWeek = startTime.dayOfWeek
        return try {
            parkingRateRepository.findByWeekDaysDay(dayOfWeek)
                .single {
                    it.isWithinRateWindow(startTime.toOffsetTime(), endTime.toOffsetTime())
                }
        } catch (e: NoSuchElementException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

class ParkingRateNotFoundException(message: String = "Rate not found"): Exception(message)
class ParkingRateOverlapsWithExistingRateException(message: String = "Rate overlaps with existing rate"): Exception(message)
class InvalidParkingRateException(message: String = "Invalid rate"): Exception(message)
