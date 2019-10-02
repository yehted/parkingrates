package com.example.rating.model

import java.time.LocalTime
import java.time.ZoneId
import javax.persistence.*

@Entity
data class ParkingRate(
    @OneToMany(mappedBy = "parkingRate", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var weekDays: List<WeekDay>? = null,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val timeZone: ZoneId,
    val price: Int,
    @Id @GeneratedValue
    var id: Long = 0
) {
    fun isWithinRateWindow(start: LocalTime, end: LocalTime): Boolean {
        return startTime.isBefore(start) && endTime.isAfter(end)
    }

    fun isOverlappingWith(otherRate: ParkingRate): Boolean? {
        return (otherRate.startTime.isAfter(startTime) && otherRate.startTime.isBefore(endTime)) ||
                (otherRate.endTime.isAfter(startTime) && otherRate.endTime.isBefore(endTime))
    }
}